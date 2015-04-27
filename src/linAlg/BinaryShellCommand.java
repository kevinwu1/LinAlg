package linAlg;

import java.util.Stack;

public abstract class BinaryShellCommand implements ShellCommand {

	@Override
	public void call(Stack<Obj<?>> st) {
		if (st.size() < 2)
			return;
		Obj<?> v1 = st.pop();
		Obj<?> v2 = st.pop();
		String t = v2.getType().concat(v1.getType());
		Obj<?> ans = null;
		switch (t) {
		case "MatScal":
			ans = call((Mat) v2, (Scal) v1);
			break;
		case "ScalMat":
			ans = call((Scal) v2, (Mat) v1);
			break;
		case "MatVec":
			ans = call((Mat) v2, (Vec<?>) v1);
			break;
		case "VecMat":
			ans = call((Vec<?>) v2, (Mat) v1);
			break;
		case "ScalVec":
			ans = call((Scal) v2, (Vec<?>) v1);
			break;
		case "VecScal":
			ans = call((Vec<?>) v2, (Scal) v1);
			break;
		case "MatMat":
			ans = call((Mat) v2, (Mat) v1);
			break;
		case "ScalScal":
			ans = call((Scal) v2, (Scal) v1);
			break;
		case "VecVec":
			ans = call((Vec<?>) v2, (Vec<?>) v1);
			break;
		default:
			System.err.println("NO MATCH: " + t);
			break;
		}
		if (ans == null) {
			st.push(v2);
			st.push(v1);
		}
		else
			st.push(ans);
	}

	public Obj<?> call(Mat m, Scal s) {
		return null;
	}

	public Obj<?> call(Scal s, Mat m) {
		return null;
	}

	public Obj<?> call(Mat m, Vec<?> s) {
		return null;
	}

	public Obj<?> call(Vec<?> s, Mat m) {
		return null;
	}

	public Obj<?> call(Scal s, Vec<?> m) {
		return null;
	}

	public Obj<?> call(Vec<?> m, Scal s) {
		return null;
	}

	public Obj<?> call(Mat m1, Mat m2) {
		return null;
	}

	public Obj<?> call(Scal s1, Scal s2) {
		return null;
	}

	public Obj<?> call(Vec<?> m1, Vec<?> m2) {
		return null;
	}

}
