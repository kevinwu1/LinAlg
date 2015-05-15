package linAlg;

import java.util.ArrayList;
import java.util.List;

public class Vec<T extends Obj<T>> extends Obj<Vec<T>> {
	private final List<T> data = new ArrayList<>();

	public static <X extends Obj<X>> Vec<X> und() {
		return new Vec<>();
	}

	private Vec() {
	}

	@SafeVarargs
	public Vec(T... q) {
		for (T t : q)
			data.add(t);
	}

	public Vec(List<T> q) {
		data.addAll(q);
	}

	public Vec(List<T> q, List<T> w) {
		data.addAll(q);
		data.addAll(w);
	}

	public int size() {
		return data.size();
	}

	T dot(Vec<T> v) {
		T o = data.get(0).mult(v.get(0));
		if (data.size() != v.size())
			throw new IllegalArgumentException("Length doesn't match");
		for (int i = 1; i < data.size(); i++)
			o = o.add(data.get(i).mult(v.get(i)));
		return o;
	}

	@Override
	public Vec<T> mult(Vec<T> t) {
		VecBuilder<T> vb = new VecBuilder<>();
		for (int i = 0; i < data.size(); i++)
			vb.push(data.get(i).mult(t.data.get(i)));
		return vb.build();
	}

	public List<T> data() {
		return data;
	}

	public T get(int i) {
		return data.get(i);
	}

	public void set(int i, T d) {
		data.set(i, d);
	}

	public static class VecBuilder<T extends Obj<T>> {
		List<T> data = new ArrayList<>();

		VecBuilder() {
		}

		@SafeVarargs
		VecBuilder(T... q) {
			for (T t : q)
				data.add(t);
		}

		VecBuilder(List<T> q) {
			data.addAll(q);
		}

		VecBuilder<T> push(T q) {
			data.add(q);
			return this;
		}

		Vec<T> build() {
			return new Vec<>(data);
		}

		@Override
		public String toString() {
			return data.toString();
		}

	}

	static Vec<Scal> ints(int... ar) {
		VecBuilder<Scal> vb = new VecBuilder<>();
		for (int i : ar)
			vb.push(new Scal(i));
		return vb.build();
	}

	@Override
	public Vec<T> mult(Scal S) {
		VecBuilder<T> vb = new VecBuilder<>();
		for (T d : data)
			vb.push(d.mult(S));
		return vb.build();
	}

	@Override
	public Vec<T> div(Scal S) {
		VecBuilder<T> vb = new VecBuilder<>();
		for (T d : data)
			vb.push(d.div(S));
		return vb.build();
	}

	@Override
	public Vec<T> add(Scal S) {
		VecBuilder<T> vb = new VecBuilder<>();
		for (T d : data)
			vb.push(d.add(S));
		return vb.build();
	}

	@Override
	public Vec<T> sub(Scal S) {
		VecBuilder<T> vb = new VecBuilder<>();
		for (T d : data)
			vb.push(d.sub(S));
		return vb.build();
	}

	@Override
	public Vec<T> add(Vec<T> t) {
		if (data.size() != t.size())
			return undef();
		VecBuilder<T> vb = new VecBuilder<>();
		for (int i = 0; i < data.size(); i++)
			vb.push(data.get(i).add(t.get(i)));
		return vb.build();
	}

	@Override
	public Vec<T> sub(Vec<T> t) {
		if (data.size() != t.size())
			return undef();
		VecBuilder<T> vb = new VecBuilder<>();
		for (int i = 0; i < data.size(); i++)
			vb.push(data.get(i).sub(t.get(i)));
		return vb.build();
	}

	@Override
	public Vec<T> clone() {
		return new Vec<>(data);
	}

	public Vec<T> rot() {
		return rot(1);
	}

	public Vec<T> rot(int n) {
		return new Vec<>(data.subList(n, data.size()), data.subList(0, n));
	}

	@Override
	public String getType() {
		return "Vec";
	}

	public List<T> getData() {
		return data;
	}

	public String getInnerType() {
		return data == null ? null : data.get(0).getType();
	}

	@Override
	public boolean equals(Object d) {
		if (d instanceof Vec<?>)
			if (data.size() == ((Vec<?>) d).data.size()) {
				for (int i = 0; i < data.size(); i++)
					if (!data.get(i).equals(((Vec<?>) d).data.get(i)))
						return false;
				return true;
			}
		// return data.equals(((Vec<?>) d).data);
		return false;
	}

	@Override
	Vec<T> undef() {
		return new Vec<>();
	}

	<X extends Obj<X>> Vec<X> undefin() {
		return new Vec<>();
	}

	@SuppressWarnings("unchecked")
	public <X extends Obj<X>> Vec<X> castTo(Class<X> c) {
		if (data.get(0).getClass().equals(c))
			return new Vec<>((List<X>) data);
		return undefin();
	}

	@Override
	public String toString() {
		return data == null ? "UNDEFINED" : data.toString().replaceAll(" ", "\t").replace("[", "(").replace("]", ")");
	}
}
