package linAlg;

import java.util.Stack;

public abstract class BinaryShellCommand implements ShellCommand {

	@Override
	public void call(Stack<Obj<?>> st) {
		if (st.size() < 2)
			return;
		Obj<?> v1 = st.pop();
		Obj<?> v2 = st.pop();
		boolean b1 = v1.getType().equals("Mat");
		boolean b2 = v2.getType().equals("Mat");
		Obj<?> ans;
		if (b1)
			if (b2)
				ans = call((Mat) v2, (Mat) v1);
			else
				ans = call((Scal) v2, (Mat) v1);
		else if (b2)
			ans = call((Mat) v2, (Scal) v1);
		else
			ans = call((Scal) v2, (Scal) v1);
		if (ans == null) {
			st.push(v2);
			st.push(v1);
		}
		else
			st.push(ans);
	}

	public Obj<?> call(Mat m1, Mat m2) {
		return null;
	}

	public Obj<?> call(Mat m, Scal s) {
		return null;
	}

	public Obj<?> call(Scal s, Mat m) {
		return null;
	}

	public Obj<?> call(Scal s1, Scal s2) {
		return null;
	}

}
