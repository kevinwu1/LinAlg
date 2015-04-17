package linAlg;

import java.util.Arrays;

import linAlg.Vec.VecBuilder;

public class Mat extends MathOb<Mat> {
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

	Scal tr() {
		if (data.size() != data.get(0).size())
			return Scal.UNDEFINED;
		Scal o = new Scal(0);
		for (int i = 0; i < data.size(); i++)
			o = o.add(data.get(i).get(i));
		return o;
	}

	Mat t() {
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

	static String matString(Vec<Vec<Scal>> mat) {
		if (mat == null)
			return "UNDEFINED";
		StringBuilder sb = new StringBuilder();
		for (Vec<Scal> v : mat.data()) {
			sb.append(v.toString());
			sb.append(System.lineSeparator());
		}
		return sb.toString().replaceAll(" ", "\t");
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
			System.out.println("MAT: " + matString(mat) + System.lineSeparator());
			for (int j = 0; j < r; j++)
				multRep(mat, r, j, mat.get(j).get(c).neg());
			for (int j = r + 1; j < mat.size(); j++)
				multRep(mat, r, j, mat.get(j).get(c).neg());
			System.out.println("MATASDF: " + matString(mat) + System.lineSeparator());
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
		return new Mat(data.mult(t.data));
	}

	@Override
	public Mat add(Mat t) {
		return new Mat(data.add(t.data));
	}

	@Override
	public Mat sub(Mat t) {
		return new Mat(data.add(t.data));
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
}
