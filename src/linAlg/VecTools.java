package linAlg;

import linAlg.Vec.VecBuilder;

public final class VecTools {
	public static Scal norm(Vec<Scal> v) {
		Scal o = Scal.ZERO;
		for (Scal s : v.getData())
			o = o.add(s.pow(2));
		return o;
		// TODO: sqrt
	}

	public static Vec<Scal> project(Vec<Scal> onto, Vec<Scal> v) {
		return onto.dot(v).div(onto.dot(onto)).mult(onto);
	}

	public static Vec<Scal> cross(Vec<Scal> v1, Vec<Scal> v2) {
		if (v1.size() != 3 || v2.size() != 3)
			return Vec.und();
		VecBuilder<Scal> vb = new VecBuilder<>();
		vb.push(v1.get(1).mult(v2.get(2)).sub(v1.get(2).mult(v2.get(1))));
		vb.push(v1.get(0).mult(v2.get(2)).sub(v1.get(2).mult(v2.get(0))).neg());
		vb.push(v1.get(0).mult(v2.get(1)).sub(v1.get(1).mult(v2.get(0))));
		return vb.build();
	}
}
