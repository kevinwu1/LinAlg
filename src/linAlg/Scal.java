package linAlg;

import static math.Mathstuff.GCF;
import static math.Mathstuff.factor;

import java.util.regex.Pattern;

public class Scal extends Obj<Scal> {
	public static final Scal ZERO = new Scal(0);
	public static final Scal UNDEFINED = new Scal(0, 0);

	int t, b;

	public Scal(int t) {
		this(t, 1);
	}

	public Scal(int t, int b) {
		this.t = t;
		this.b = b;
		reduce();
	}

	public <T extends Obj<T>> Vec<T> mult(Vec<T> v) {
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
	public Scal mult(Scal S) {
		return new Scal(t * S.t, b * S.b);
	}

	@Override
	public Scal div(Scal S) {
		return new Scal(t * S.b, b * S.t);
	}

	@Override
	public Scal add(Scal S) {
		int[] b1 = factor(b);
		int[] b2 = factor(S.b);
		int t1 = t;
		int t2 = S.t;
		int bot = 1;
		int i, j;
		for (i = 0, j = 0; i < b1.length && j < b2.length;)
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
		while (j < b2.length) {
			bot *= b2[j];
			t1 *= b2[j++];
		}
		while (i < b1.length) {
			bot *= b1[i];
			t2 *= b1[i++];
		}
		return new Scal(t1 + t2, bot);
	}

	public Scal neg() {
		return new Scal(-t, b);
	}

	public Scal inverse() {
		return new Scal(-b, t);
	}

	public Scal recip() {
		return new Scal(b, t);
	}

	public Scal pow(int i) {
		if (i < 0)
			return inverse().pow(-i);
		return new Scal((int) Math.pow(t, i), (int) Math.pow(b, i));
	}

	@Override
	public Scal sub(Scal S) {
		return add(S.neg());
	}

	public boolean equals(Scal S) {
		if (t == 0)
			return S.t == 0;
		return t == S.t && b == S.b;
	}

	@Override
	Scal undef() {
		return UNDEFINED;
	}

	@Override
	String getType() {
		return "Scal";
	}

	public static Scal parse(String s) {
		if (s.contains("/") && Pattern.matches("-?\\d+/\\d+", s)) {
			return new Scal(Integer.parseInt(s.split("/")[0]), Integer.parseInt(s.split("/")[1]));
		}
		else if (Pattern.matches("-?/\\d+", s)) {
			return new Scal(1, Integer.parseInt(s.substring(1)));
		}
		else if (s.matches("-?\\d+"))
			return new Scal(Integer.parseInt(s));
		return UNDEFINED;
	}
}
