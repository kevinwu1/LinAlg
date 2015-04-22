package linAlg;

public abstract class Obj<S extends Obj<S>> implements Mult<S>, Scalable<S> {
	abstract String getType();

	abstract S undef();
}
