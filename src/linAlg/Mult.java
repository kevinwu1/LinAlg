package linAlg;

public interface Mult<T extends Obj<T>> {
	public T mult(T t);

	public T add(T t);

	public T sub(T t);
}
