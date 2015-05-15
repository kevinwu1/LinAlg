package linAlg;

import java.util.Arrays;
import java.util.stream.Collectors;

import linAlg.Vec.VecBuilder;

public class Mat extends Obj<Mat> {
	public static final Mat UNDEFINED = new Mat();
	static final String type = "Mat";

	Vec<Vec<Scal>> data;

	private Mat() {
	}

	Mat(Vec<Vec<Scal>> data) {
		this.data = data;
	}

	Mat(int r, int c, final int... d) {
		this.data = build(r, c, d);
	}

	Mat(int r, int c, final Scal... d) {
		this.data = build(r, c, d);
	}

	static Mat I(int i) {
		VecBuilder<Vec<Scal>> vb = new VecBuilder<>();
		for (int j = 0; j < i; j++) {
			VecBuilder<Scal> vs = new VecBuilder<>();
			int k = 0;
			for (; k < j; k++)
				vs.push(new Scal(0));
			vs.push(new Scal(1));
			for (k++; k < i; k++)
				vs.push(new Scal(0));
			vb.push(vs.build());
		}
		return new Mat(vb.build());
	}

	Scal tr() {
		if (data.size() != data.get(0).size())
			return Scal.UNDEFINED;
		Scal o = new Scal(0);
		for (int i = 0; i < data.size(); i++)
			o = o.add(data.get(i).get(i));
		return o;
	}

	Mat T() {
		VecBuilder<Vec<Scal>> vb = new VecBuilder<>();
		for (int i = 0; i < data.get(0).size(); i++) {
			VecBuilder<Scal> inV = new VecBuilder<>();
			for (int j = 0; j < data.size(); j++)
				inV.push(data.get(j).get(i));
			vb.push(inV.build());
		}
		return new Mat(vb.build());
	}

	static Vec<Vec<Scal>> build(int r, int c, final int... d) {
		VecBuilder<Vec<Scal>> vb = new VecBuilder<>();
		for (int i = 0; i < r; i++)
			vb.push(Vec.ints(Arrays.copyOfRange(d, i * c, (i + 1) * c)));
		return vb.build();
	}

	static Vec<Vec<Scal>> build(int r, int c, final Scal... d) {
		VecBuilder<Vec<Scal>> vb = new VecBuilder<>();
		for (int i = 0; i < r; i++)
			vb.push(new Vec<>(Arrays.copyOfRange(d, i * c, (i + 1) * c)));
		return vb.build();
	}

	static String matString(Vec<Vec<Scal>> mat) {
		if (mat == null)
			return "UNDEFINED";
		StringBuilder sb = new StringBuilder();
		for (Vec<Scal> v : mat.data()) {
			sb.append(v.toString());
			sb.append(System.lineSeparator());
		}
		return sb.toString().replaceAll(",", "").replaceAll(" ", "\t").replaceAll("\\(", "[").replaceAll("\\)", "]");
	}

	static Vec<Vec<Scal>> rref(Vec<Vec<Scal>> mat) {
		mat = mat.clone();
		ou: for (int c = 0, r = 0; c < mat.get(0).size() && r < mat.size(); c++, r++) {
			while (firstNonZero(mat.get(r)) != c) {
				if (c == mat.size())
					break ou;
				int j;
				for (j = r; j < mat.size(); j++)
					if (!mat.get(j).get(c).equals(Scal.ZERO)) {
						swap(mat, r, j);
						break;
					}
				if (j == mat.size())
					c++;
			}
			mat.set(r, mat.get(r).mult(mat.get(r).get(c).recip()));
			for (int j = 0; j < r; j++)
				multRep(mat, r, j, mat.get(j).get(c).neg());
			for (int j = r + 1; j < mat.size(); j++)
				multRep(mat, r, j, mat.get(j).get(c).neg());
		}
		return mat;
	}

	static Vec<Vec<Scal>> ref(Vec<Vec<Scal>> mat) {
		mat = mat.clone();
		ou: for (int c = 0, r = 0; c < mat.get(0).size() && r < mat.size(); c++, r++) {
			while (firstNonZero(mat.get(r)) != c) {
				if (c == mat.size())
					break ou;
				int j;
				for (j = r; j < mat.size(); j++)
					if (!mat.get(j).get(c).equals(Scal.ZERO)) {
						swap(mat, r, j);
						break;
					}
				if (j == mat.size())
					c++;
			}
			mat.set(r, mat.get(r).mult(mat.get(r).get(c).recip()));
			for (int j = r + 1; j < mat.size(); j++)
				multRep(mat, r, j, mat.get(j).get(c).neg());
		}
		return mat;
	}

	static int firstNonZero(Vec<Scal> v) {
		for (int i = 0; i < v.size(); i++)
			if (!v.get(i).equals(Scal.ZERO))
				return i;
		return -1;
	}

	static void multRep(Vec<Vec<Scal>> mat, int from, int to, Scal mult) {
		mat.set(to, mat.get(from).mult(mult).add(mat.get(to)));
	}

	static void swap(Vec<Vec<Scal>> mat, int i, int j) {
		Vec<Scal> temp = mat.get(i);
		mat.set(i, mat.get(j));
		mat.set(j, temp);
	}

	public Mat rref() {
		return new Mat(rref(data));
	}

	public Mat ref() {
		return new Mat(ref(data));
	}

	public Vec<Scal> row(int r) {
		if (r < 0 || r >= data.size())
			return Vec.und();
		return data.get(r);
	}

	public Vec<Scal> col(int c) {
		if (c < 0 || c >= data.get(0).size())
			return Vec.und();
		VecBuilder<Scal> vb = new VecBuilder<>();
		for (int i = 0; i < data.size(); i++)
			vb.push(data.get(i).get(c));
		return vb.build();
	}

	@Override
	public String toString() {
		return matString(data);
	}

	@Override
	Mat undef() {
		return UNDEFINED;
	}

	@Override
	String getType() {
		return "Mat";
	}

	@Override
	public Mat mult(Mat t) {
		if (this.equals(undef()) || t.equals(undef()))
			return undef();
		VecBuilder<Vec<Scal>> vb = new VecBuilder<>();
		if (data.get(0).size() != t.data.size())
			return undef();
		for (int i = 0; i < data.size(); i++) {
			VecBuilder<Scal> vs = new VecBuilder<>();
			for (int j = 0; j < t.data.get(0).size(); j++) {
				vs.push(row(i).dot(t.col(j)));
			}
			vb.push(vs.build());
		}
		return new Mat(vb.build());
	}

	@Override
	public Mat add(Mat t) {
		if (isUndefined(this) || isUndefined(t) || data.size() != t.data.size() || data.get(0).size() != t.data.get(0).size())
			return undef();
		return new Mat(data.add(t.data));
	}

	@Override
	public Mat sub(Mat t) {
		return new Mat(data.sub(t.data));
	}

	@Override
	public Mat mult(Scal S) {
		return new Mat(data.mult(S));
	}

	@Override
	public Mat div(Scal S) {
		return new Mat(data.div(S));
	}

	@Override
	public Mat add(Scal S) {
		return new Mat(data.add(S));
	}

	@Override
	public Mat sub(Scal S) {
		return new Mat(data.sub(S));
	}

	public Mat mult(int S) {
		return new Mat(data.mult(new Scal(S)));
	}

	public Mat div(int S) {
		return new Mat(data.div(new Scal(S)));
	}

	public Mat add(int S) {
		return new Mat(data.add(new Scal(S)));
	}

	public Mat sub(int S) {
		return new Mat(data.sub(new Scal(S)));
	}

	public Scal get(int i, int j) {
		return data.get(i).get(j);
	}

	public Scal det() {
		if (isUndefined(this) || data.size() != data.get(0).size())
			return Scal.UNDEFINED;
		if (data.size() == 1)
			return data.get(0).get(0);
		Scal o = Scal.ZERO;
		for (int i = 0; i < data.size(); i++) {
			o = o.add(data.get(0).get(i).mult(without(0, i).det()).mult(i % 2 == 1 ? new Scal(-1) : new Scal(1)));
		}
		return o;
	}

	public Mat minors() {
		VecBuilder<Vec<Scal>> vb = new VecBuilder<>();
		for (int i = 0; i < data.size(); i++) {
			VecBuilder<Scal> vs = new VecBuilder<>();
			for (int j = 0; j < data.size(); j++) {
				vs.push(without(i, j).det());
			}
			vb.push(vs.build());
		}
		return new Mat(vb.build());
	}

	public Mat cofacts() {
		VecBuilder<Vec<Scal>> vb = new VecBuilder<>();
		for (int i = 0; i < data.size(); i++) {
			VecBuilder<Scal> vs = new VecBuilder<>();
			for (int j = 0; j < data.size(); j++) {
				vs.push(without(i, j).det().mult(new Scal((i + j) % 2 == 0 ? 1 : -1)));
			}
			System.out.println("vs: " + vs);
			vb.push(vs.build());
		}
		System.out.println("vb: " + vb);
		return new Mat(vb.build());
	}

	private Mat without(int i, int j) {
		VecBuilder<Vec<Scal>> vb = new VecBuilder<>();
		for (int a = 0; a < data.size(); a++)
			if (a != i) {
				VecBuilder<Scal> vs = new VecBuilder<>();
				for (int b = 0; b < data.get(0).size(); b++)
					if (b != j)
						vs.push(data.get(a).get(b));
				vb.push(vs.build());
			}
		return new Mat(vb.build());
	}

	public Mat inverse() {
		if (!isSquare(this))
			return UNDEFINED;
		Mat[] x = augment(I(data.size())).rref().splitCol(data.size());
		if (x[0].equals(I(data.size())))
			return x[1];
		return UNDEFINED;
		// Vec<Vec<Scal>> o = I(data.size()).data;
		// Vec<Vec<Scal>> mat = data.clone();
		// ou: for (int c = 0, r = 0; c < mat.get(0).size() && r < mat.size(); c++, r++) {
		// while (firstNonZero(mat.get(r)) != c) {
		// if (c == mat.size())
		// break ou;
		// int j;
		// for (j = r; j < mat.size(); j++)
		// if (!mat.get(j).get(c).equals(Scal.ZERO)) {
		// swap(o, r, j);
		// swap(mat, r, j);
		// break;
		// }
		// if (j == mat.size())
		// c++;
		// }
		// o.set(r, o.get(r).mult(mat.get(r).get(c).recip()));
		// mat.set(r, mat.get(r).mult(mat.get(r).get(c).recip()));
		// for (int j = 0; j < r; j++) {
		// multRep(o, r, j, mat.get(j).get(c).neg());
		// multRep(mat, r, j, mat.get(j).get(c).neg());
		// }
		// for (int j = r + 1; j < mat.size(); j++) {
		// multRep(o, r, j, mat.get(j).get(c).neg());
		// multRep(mat, r, j, mat.get(j).get(c).neg());
		// }
		// }
		// return I(data.size()).data.equals(mat) ? new Mat(o) : UNDEFINED;

		// Scal det = det();
		// System.out.println("det: " + det.toString());
		// if (!det.equals(Scal.ZERO) && !det.equals(Scal.UNDEFINED))
		// return new Mat(2, 2, get(1, 1), get(0, 1).neg(), get(1, 0).neg(), get(0, 0)).div(det);
		// return Mat.UNDEFINED;
	}

	public Mat pow(int i) {
		if (i < 0)
			return inverse().pow(-i);
		Mat o = this;
		for (int j = 1; j < i; j++) {
			o = o.mult(this);
		}
		return o;
	}

	public Mat augment(Mat mat) {
		if (data.size() != mat.data.size())
			return UNDEFINED;
		VecBuilder<Vec<Scal>> ou = new VecBuilder<>();
		for (int i = 0; i < data.size(); i++) {
			ou.push(new Vec<>(java.util.stream.Stream.concat(data.get(i).data().stream(), mat.data.get(i).data().stream()).collect(Collectors.toList())));
		}
		return new Mat(ou.build());
	}

	public Mat[] splitCol(int pos) {
		VecBuilder<Vec<Scal>> l = new VecBuilder<>();
		VecBuilder<Vec<Scal>> r = new VecBuilder<>();
		for (Vec<Scal> v : data.data()) {
			VecBuilder<Scal> pl = new VecBuilder<>();
			VecBuilder<Scal> pr = new VecBuilder<>();
			for (int i = 0; i < v.size(); i++) {
				if (i < pos)
					pl.push(v.get(i));
				else
					pr.push(v.get(i));
			}
			l.push(pl.build());
			r.push(pr.build());
		}
		return new Mat[] { new Mat(l.build()), new Mat(r.build()) };
	}

	@Override
	public boolean equals(Object t) {
		if (t instanceof Mat) {
			if (data == null ^ ((Mat) t).data == null)
				return false;
			if (data == null && ((Mat) t).data == null)
				return true;
			return data.equals(((Mat) t).data);
		}
		return false;
	}

	public static boolean isUndefined(Mat mat) {
		return mat.data == null;
	}

	public static boolean isSquare(Mat mat) {
		return !isUndefined(mat) && mat.data.size() == mat.data.get(0).size();
	}
}
