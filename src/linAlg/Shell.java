package linAlg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Pattern;

import linAlg.Vec.VecBuilder;

public class Shell {
	private final Stack<Obj<?>> st = new Stack<>();
	private final Map<String, Obj<?>> mem = new HashMap<>();
	Map<String, ShellCommand> comms = new HashMap<>();
	private final List<String> hist = new ArrayList<>();

	public void addAll(Map<String, ? extends Obj<?>> mem) {
		this.mem.putAll(mem);
	}

	private void addComm(String name, ShellCommand comm) {
		comms.put(name, comm);
	}

	private void addComm(String[] names, ShellCommand comm) {
		for (String name : names)
			addComm(name, comm);
	}

	Shell(final Scanner scan) {
		addComm(new String[] { "`", "st" }, new ShellCommand() {
			@Override
			public void call(Stack<Obj<?>> st) {
			}
		});
		addComm("pop", new ShellCommand() {
			@Override
			public void call(Stack<Obj<?>> st) {
				System.out.println(st.pop());
			}
		});
		addComm("mem", new ShellCommand() {
			@Override
			public void call(Stack<Obj<?>> st) {
				System.out.println(stringMem());
			}
		});
		addComm("clear", new ShellCommand() {
			@Override
			public void call(Stack<Obj<?>> st) {
				st.clear();
			}
		});
		addComm(new String[] { "t", "T" }, new UnaryShellCommand() {
			@Override
			public Obj<Mat> call(Mat m) {
				return m.T();
			}
		});
		addComm(new String[] { "tr", "TR" }, new UnaryShellCommand() {
			@Override
			public Obj<Scal> call(Mat m) {
				return m.tr();
			}
		});
		addComm(new String[] { "ref", "REF" }, new UnaryShellCommand() {
			@Override
			public Obj<Mat> call(Mat m) {
				return m.ref();
			}
		});
		addComm(new String[] { "rref", "RREF" }, new UnaryShellCommand() {
			@Override
			public Obj<Mat> call(Mat m) {
				return m.rref();
			}
		});
		addComm("norm", new UnaryShellCommand() {
			@Override
			public Obj<Scal> call(Vec<?> v) {
				return VecTools.norm(v.castTo(Scal.class));
			}
		});
		addComm("det", new UnaryShellCommand() {
			@Override
			public Obj<Scal> call(Mat m) {
				return m.det();
			}
		});
		addComm("inv", new UnaryShellCommand() {
			@Override
			public Obj<Mat> call(Mat m) {
				return m.inverse();
			}

			@Override
			public Obj<Scal> call(Scal s) {
				return s.inverse();
			}
		});
		addComm("minors", new UnaryShellCommand() {
			@Override
			public Obj<Mat> call(Mat m) {
				return m.minors();
			}
		});
		addComm("cofacts", new UnaryShellCommand() {
			@Override
			public Obj<Mat> call(Mat m) {
				return m.cofacts();
			}
		});
		addComm("matI", new UnaryShellCommand() {
			@Override
			public Obj<Mat> call(Scal s) {
				return Mat.I(s.t);
			}
		});
		addComm("toVecs", new UnaryShellCommand() {
			@Override
			public Obj<Vec<Vec<Scal>>> call(Mat m) {
				return new Vec<>(m.data.data());
			}
		});
		addComm("+", new BinaryShellCommand() {
			@Override
			public Obj<Mat> call(Mat m1, Mat m2) {
				return m1.add(m2);
			}

			@Override
			public Obj<Scal> call(Scal s1, Scal s2) {
				return s1.add(s2);
			}

			@Override
			public Obj<Vec<Scal>> call(Vec<?> v1, Vec<?> v2) {
				return v1.castTo(Scal.class).add(v2.castTo(Scal.class));
			}
		});
		addComm("-", new BinaryShellCommand() {
			@Override
			public Obj<Mat> call(Mat m1, Mat m2) {
				return m1.sub(m2);
			}

			@Override
			public Obj<Scal> call(Scal s1, Scal s2) {
				return s1.sub(s2);
			}

			@Override
			public Obj<Vec<Scal>> call(Vec<?> v1, Vec<?> v2) {
				return v1.castTo(Scal.class).sub(v2.castTo(Scal.class));
			}
		});
		addComm("*", new BinaryShellCommand() {
			@Override
			public Obj<Mat> call(Mat m1, Mat m2) {
				return m1.mult(m2);
			}

			@Override
			public Obj<Mat> call(Mat m, Scal s) {
				return m.mult(s);
			}

			@Override
			public Obj<Mat> call(Scal s, Mat m) {
				return m.mult(s);
			}

			@Override
			public Obj<Scal> call(Scal s1, Scal s2) {
				return s1.mult(s2);
			}

			@Override
			public Obj<Vec<Scal>> call(Vec<?> s1, Scal s2) {
				return s1.castTo(Scal.class).mult(s2);
			}

			@Override
			public Obj<Vec<Scal>> call(Scal s1, Vec<?> s2) {
				return s2.castTo(Scal.class).mult(s1);
			}
		});
		addComm("/", new BinaryShellCommand() {
			@Override
			public Obj<Mat> call(Mat m, Scal s) {
				return m.div(s);
			}

			@Override
			public Obj<Mat> call(Scal s, Mat m) {
				return m.div(s);
			}

			@Override
			public Obj<Scal> call(Scal s1, Scal s2) {
				return s1.div(s2);
			}

			@Override
			public Obj<Vec<Scal>> call(Vec<?> m, Scal s) {
				return m.castTo(Scal.class).div(s);
			}

			@Override
			public Obj<Vec<Scal>> call(Scal s, Vec<?> m) {
				return m.castTo(Scal.class).div(s);
			}
		});
		addComm("aug", new BinaryShellCommand() {
			@Override
			public Obj<Mat> call(Mat m1, Mat m2) {
				return m1.augment(m2);
			}
		});
		addComm("pow", new BinaryShellCommand() {
			@Override
			public Obj<Mat> call(Mat m, Scal s) {
				return m.pow(s.t);
			}

			@Override
			public Obj<Scal> call(Scal s1, Scal s2) {
				return s1.pow(s2.t);
			}
		});
		addComm("dot", new BinaryShellCommand() {
			@Override
			public Obj<Scal> call(Vec<?> v1, Vec<?> v2) {
				return v1.castTo(Scal.class).dot(v2.castTo(Scal.class));
			}
		});
		addComm("cross", new BinaryShellCommand() {
			@Override
			public Obj<Vec<Scal>> call(Vec<?> v1, Vec<?> v2) {
				return VecTools.cross(v1.castTo(Scal.class), v2.castTo(Scal.class));
			}
		});
		addComm("proj", new BinaryShellCommand() {
			@Override
			public Obj<Vec<Scal>> call(Vec<?> v1, Vec<?> v2) {
				return VecTools.project(v1.castTo(Scal.class), v2.castTo(Scal.class));
			}
		});
		addComm("split", new BinaryShellCommand() {
			@Override
			public Obj<?> call(Mat m, Scal s) {
				if (s.b != 1)
					return null;
				Mat[] spl = m.splitCol(s.t);
				st.push(spl[0]);
				return spl[1];
			}
		});
		addComm("vec", new ShellCommand() {
			@Override
			public void call(Stack<Obj<?>> st) {
				int r = scan.nextInt();
				hist.add(Integer.toString(r));
				VecBuilder<Scal> vs = new VecBuilder<>();
				for (int i = 0; i < r; i++) {
					int n = scan.nextInt();
					hist.add(Integer.toString(n));
					vs.push(new Scal(n));
				}
				st.push(vs.build());
			}
		});
		addComm("mat", new ShellCommand() {
			@Override
			public void call(Stack<Obj<?>> st) {
				Obj<?> oc = st.pop();
				Obj<?> or = st.pop();
				int r = 0;
				int c = 0;
				if (or instanceof Scal)
					if (((Scal) or).b == 1)
						r = ((Scal) or).t;
				if (oc instanceof Scal)
					if (((Scal) oc).b == 1)
						c = ((Scal) oc).t;
				if (r == 0 || c == 0) {
					st.push(or);
					st.push(oc);
					return;
				}
				Scal[] d = new Scal[r * c];
				for (int i = r * c - 1; i >= 0; i--) {
					Obj<?> nu = st.pop();
					if (nu instanceof Scal)
						d[i] = (Scal) nu;
					else {
						for (i++; i < r * c;)
							st.push(d[i++]);
						st.push(or);
						st.push(oc);
						return;
					}
				}
				st.push(new Mat(r, c, d));
			}
		});
		addComm("swap", new ShellCommand() {
			@Override
			public void call(Stack<Obj<?>> st) {
				Obj<?> t = st.pop();
				Obj<?> b = st.pop();
				st.push(t);
				st.push(b);
			}
		});
		addComm("hist", new ShellCommand() {
			@Override
			public void call(Stack<Obj<?>> st) {
				for (String s : hist)
					System.out.println(s);
			}
		});
	}

	void eval(String s) {
		hist.add(s);
		System.out.println(s);
		if (s.contains("="))
			mem.put(s.split("=")[0], st.pop());
		else if (comms.containsKey(s)) {
			System.out.println(s);
			comms.get(s).call(st);
		}
		else {
			if (Pattern.matches("-?[0-9]+", s))
				st.push(new Scal(Integer.parseInt(s)));
			else if (Pattern.matches("\\w=", s))
				mem.put(s.substring(0, s.length() - 1), st.peek());
			else if (mem.containsKey(s))
				st.push(mem.get(s));
		}
		System.out.println(stringStack());
	}

	String stringStack() {
		if (st.isEmpty())
			return "| Empty Stack";
		List<String[]> items = new ArrayList<>();
		int size = 0;
		for (Obj<?> s : st) {
			String[] str = s.toString().trim().split(System.lineSeparator());
			items.add(str);
			size = Math.max(size, str.length);
		}
		StringBuilder o = new StringBuilder();
		for (int i = 0; i < size; i++) {
			o.append("|\t");
			for (int j = 0; j < items.size(); j++)
				if (i < items.get(j).length)
					o.append(items.get(j)[i] + "\t");
				else
					for (int k = 0; k < items.get(j)[0].split("\t").length; k++)
						for (int l = 0; l < items.get(j)[0].split("\t")[k].length(); l += 8)
							o.append("\t");
			o.append(System.lineSeparator());
		}
		return o.toString();
	}

	String stringMem() {
		List<String[]> items = new ArrayList<>();
		int size = 0;
		for (String s : mem.keySet()) {
			String[] str = (s + System.lineSeparator() + mem.get(s).toString()).trim().split(System.lineSeparator());
			items.add(str);
			size = Math.max(size, str.length);
		}
		StringBuilder o = new StringBuilder();
		for (int j = 0; j < items.size(); j++) {
			o.append(items.get(j)[0]);
			for (int i = 0; i < items.get(j)[1].split("\t").length; i++)
				o.append("\t");
		}
		o.append(System.lineSeparator());
		for (int i = 1; i < size; i++) {
			for (int j = 0; j < items.size(); j++)
				if (i < items.get(j).length)
					o.append(items.get(j)[i] + "\t");
				else
					for (int k = 0; k < items.get(j)[1].split("\t").length; k++)
						o.append("\t");
			o.append(System.lineSeparator());
		}
		return o.toString();
	}
}
