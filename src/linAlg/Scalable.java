package linAlg;

public interface Scalable<T> {
	public T mult(Scalar S);

	public T div(Scalar S);

	public T add(Scalar S);

	public T sub(Scalar S);
}
