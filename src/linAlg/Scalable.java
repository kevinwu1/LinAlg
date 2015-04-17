package linAlg;

public interface Scalable<T> {
	public T mult(Scal S);

	public T div(Scal S);

	public T add(Scal S);

	public T sub(Scal S);
}
