package linAlg;

import java.util.Arrays;

import linAlg.Vec.VecBuilder;

public class Mat extends Self<Mat> {
	public static final Mat UNDEFINED = new Mat();

	Vec<Vec<Scalar>> data;

	private Mat() {
	}

	Mat(Vec<Vec<Scalar>> data) {
		this.data = data;
	}

	Mat(int r, int c, final int... d) {
		this.data = build(r, c, d);
	}

	Scalar tr() {
		if (data.size() != data.get(0).size())
			return Scalar.UNDEFINED;
		Scalar o = new Scalar(0);
		for (int i = 0; i < data.size(); i++) {
			o = o.add(data.get(i).get(i));
		}
		return o;
	}

	Mat t() {
		VecBuilder<Vec<Scalar>> vb = new VecBuilder<>();
		for (int i = 0; i < data.get(0).size(); i++) {
			VecBuilder<Scalar> inV = new VecBuilder<>();
			for (int j = 0; j < data.size(); j++) {
				inV.push(data.get(j).get(i));
			}
			vb.push(inV.build());
		}
		return new Mat(vb.build());
	}

	static Vec<Vec<Scalar>> build(int r, int c, final int... d) {
		VecBuilder<Vec<Scalar>> vb = new VecBuilder<>();
		for (int i = 0; i < r; i++)
			vb.push(Vec.ints(Arrays.copyOfRange(d, i * c, (i + 1) * c)));
		return vb.build();
	}

	static String matString(Vec<Vec<Scalar>> mat) {
		if (mat == null)
			return "UNDEFINED";
		StringBuilder sb = new StringBuilder();
		for (Vec<Scalar> v : mat.data()) {
			sb.append(v.toString());
			sb.append(System.lineSeparator());
		}
		return sb.toString().replaceAll(" ", "\t");
	}

	static Vec<Vec<Scalar>> rref(Vec<Vec<Scalar>> mat) {
		mat = mat.clone();
		ou: for (int c = 0, r = 0; c < mat.get(0).size() && r < mat.size(); c++, r++) {
			while (firstNonZero(mat.get(r)) != c) {
				if (c == mat.size())
					break ou;
				int j;
				for (j = r; j < mat.size(); j++) {
					if (!mat.get(j).get(c).equals(Scalar.ZERO)) {
						swap(mat, r, j);
						break;
					}
				}
				if (j == mat.size())
					c++;
			}
			mat.set(r, mat.get(r).mult(mat.get(r).get(c).recip()));
			System.out.println("MAT: " + matString(mat) + System.lineSeparator());
			for (int j = 0; j < r; j++) {
				multRep(mat, r, j, mat.get(j).get(c).neg());
			}
			for (int j = r + 1; j < mat.size(); j++) {
				multRep(mat, r, j, mat.get(j).get(c).neg());
			}
			System.out.println("MATASDF: " + matString(mat) + System.lineSeparator());
		}
		return mat;
	}

	static int firstNonZero(Vec<Scalar> v) {
		for (int i = 0; i < v.size(); i++)
			if (!v.get(i).equals(Scalar.ZERO))
				return i;
		return -1;
	}

	static void multRep(Vec<Vec<Scalar>> mat, int from, int to, Scalar mult) {
		mat.set(to, mat.get(from).mult(mult).add(mat.get(to)));
	}

	static void swap(Vec<Vec<Scalar>> mat, int i, int j) {
		Vec<Scalar> temp = mat.get(i);
		mat.set(i, mat.get(j));
		mat.set(j, temp);
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
	public Mat mult(Scalar S) {
		return new Mat(data.mult(S));
	}

	@Override
	public Mat div(Scalar S) {
		return new Mat(data.div(S));
	}

	@Override
	public Mat add(Scalar S) {
		return new Mat(data.add(S));
	}

	@Override
	public Mat sub(Scalar S) {
		return new Mat(data.sub(S));
	}

	public Mat mult(int S) {
		return new Mat(data.mult(new Scalar(S)));
	}

	public Mat div(int S) {
		return new Mat(data.div(new Scalar(S)));
	}

	public Mat add(int S) {
		return new Mat(data.add(new Scalar(S)));
	}

	public Mat sub(int S) {
		return new Mat(data.sub(new Scalar(S)));
	}

	@Override
	Mat self() {
		return this;
	}

	@Override
	public String toString() {
		return matString(data);
	}

	@Override
	Mat undef() {
		return UNDEFINED;
	}
}
