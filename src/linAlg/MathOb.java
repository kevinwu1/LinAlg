package linAlg;

public abstract class MathOb<S extends MathOb<S>> implements Mult<S>, Scalable<S> {
	abstract String getType();

	abstract S undef();
}
