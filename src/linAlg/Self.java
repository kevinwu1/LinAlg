package linAlg;

public abstract class Self<S extends Self<S>> implements Mult<S>, Scalable<S> {
	abstract S self();

	abstract S undef();
	// @Override
	// public abstract S mult(S s);
	//
	// @Override
	// public abstract S mult(Scalar S);
}
