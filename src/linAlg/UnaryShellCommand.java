package linAlg;

import java.util.Stack;

public abstract class UnaryShellCommand implements ShellCommand {
	@Override
	public void call(Stack<Obj<?>> st) {
		if (st.size() < 1)
			return;
		Obj<?> v1 = st.pop();
		Obj<?> ans = null;
		switch (v1.getType()) {
		case "Mat":
			ans = call((Mat) v1);
			break;
		case "Scal":
			ans = call((Scal) v1);
			break;
		case "Vec":
			ans = call((Vec<?>) v1);
			break;
		default:
			System.err.println("NO MATCH: " + v1.getType());
			break;
		}
		if (ans == null)
			st.push(v1);
		else
			st.push(ans);
	}

	public Obj<?> call(Mat m) {
		return null;
	}

	public Obj<?> call(Scal s) {
		return null;
	}

	public Obj<?> call(Vec<?> v) {
		return null;
	}

}
