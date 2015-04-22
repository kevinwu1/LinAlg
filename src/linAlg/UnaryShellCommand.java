package linAlg;

import java.util.Stack;

public abstract class UnaryShellCommand implements ShellCommand {
	@Override
	public void call(Stack<Obj<?>> st) {
		if (st.size() < 1)
			return;
		Obj<?> v1 = st.pop();
		boolean b1 = v1.getType().equals("Mat");
		Obj<?> ans;
		if (b1)
			ans = call((Mat) v1);
		else
			ans = call((Scal) v1);
		if (ans == null) {
			st.push(v1);
		}
		else
			st.push(ans);
	}

	public Obj<?> call(Mat m) {
		return null;
	}

	public Obj<?> call(Scal s) {
		return null;
	}

}
