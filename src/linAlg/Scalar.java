package linAlg;

import static math.MathT.GCF;
import static math.MathT.factor;

public class Scalar extends Self<Scalar> {
	public static final Scalar ZERO = new Scalar(0);
	public static final Scalar UNDEFINED = new Scalar(0, 0);

	int t, b;

	public Scalar(int t) {
		this(t, 1);
	}

	public Scalar(int t, int b) {
		this.t = t;
		this.b = b;
		reduce();
	}

	public <T extends Self<T>> Vec<T> mult(Vec<T> v) {
		return null;
	}

	void reduce() {
		if (t == 0) {
			if (b != 0)
				b = 1;
		}
		else {
			if (b < 0) {
				t = -t;
				b = -b;
			}
			int g = GCF(t, b);
			// System.out.println(t + "/:/" + b);
			// System.out.println(g);
			t /= g;
			b /= g;
		}
	}

	@Override
	public String toString() {
		return t == 0 ? b == 0 ? "UNDEFINED" : "0" : t + (b == 1 ? "" : "/" + b);
	}

	@Override
	Scalar self() {
		return this;
	}

	@Override
	public Scalar mult(Scalar S) {
		return new Scalar(t * S.t, b * S.b);
	}

	@Override
	public Scalar div(Scalar S) {
		return new Scalar(t * S.b, b * S.t);
	}

	@Override
	public Scalar add(Scalar S) {
		int[] b1 = factor(b);
		int[] b2 = factor(S.b);
		int t1 = t;
		int t2 = S.t;
		int bot = 1;
		int i, j;
		for (i = 0, j = 0; i < b1.length && j < b2.length;) {
			if (b1[i] < b2[j]) {
				bot *= b1[i];
				t2 *= b1[i++];
			}
			else if (b1[i] > b2[j]) {
				bot *= b2[j];
				t1 *= b2[j++];
			}
			else {
				bot *= b1[i++];
				j++;
			}
		}
		while (j < b2.length) {
			bot *= b2[j];
			t1 *= b2[j++];
		}
		while (i < b1.length) {
			bot *= b1[i];
			t2 *= b1[i++];
		}
		return new Scalar(t1 + t2, bot);
	}

	public Scalar neg() {
		return new Scalar(-t, b);
	}

	public Scalar inverse() {
		return new Scalar(-b, t);
	}

	public Scalar recip() {
		return new Scalar(b, t);
	}

	@Override
	public Scalar sub(Scalar S) {
		return add(S.neg());
	}

	public boolean equals(Scalar S) {
		if (t == 0)
			return S.t == 0;
		return t == S.t && b == S.b;
	}

	@Override
	Scalar undef() {
		return UNDEFINED;
	}
}
