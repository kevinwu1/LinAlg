package linAlg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Shell {
	private final Stack<MathOb<?>> st = new Stack<>();
	private final Map<String, MathOb<?>> mem = new HashMap<>();

	Shell(Map<String, ? extends MathOb<?>> mem) {
		this.mem.putAll(mem);
	}

	void eval(String s) {
		if (s.contains("="))
			mem.put(s.split("=")[0], st.pop());
		else
			switch (s) {
			case "`": // just print stack
				break;
			case "pop":
				System.out.println(st.pop());
				break;
			case "mem":
				System.out.println(stringMem());
				break;
			case "*": {
				if (st.size() < 2)
					break;
				MathOb<?> v1 = st.pop();
				MathOb<?> v2 = st.pop();
				boolean b1 = v1.getType().equals("Mat");
				boolean b2 = v2.getType().equals("Mat");
				if (b1)
					if (b2)
						st.push(((Mat) v1).mult((Mat) v2));
					else
						st.push(((Mat) v1).mult((Scal) v2));
				else if (b2)
					st.push(((Mat) v2).mult((Scal) v1));
				else
					st.push(((Scal) v2).mult((Scal) v1));
				break;
			}
			case "/": {
				if (st.size() < 2)
					break;
				MathOb<?> v1 = st.pop();
				MathOb<?> v2 = st.pop();
				boolean b1 = v1.getType().equals("Mat");
				boolean b2 = v2.getType().equals("Mat");
				if (b1)
					if (b2) {
						st.push(v2);
						st.push(v1);
					}
					else
						st.push(((Mat) v1).div((Scal) v2));
				else if (b2)
					st.push(((Mat) v2).div((Scal) v1));
				else
					st.push(((Scal) v2).div((Scal) v1));
				break;
			}
			case "+": {
				if (st.size() < 2)
					break;
				MathOb<?> v1 = st.pop();
				MathOb<?> v2 = st.pop();
				boolean b1 = v1.getType().equals("Mat");
				boolean b2 = v2.getType().equals("Mat");
				if (b1)
					if (b2)
						st.push(((Mat) v1).add((Mat) v2));
					else
						st.push(((Mat) v1).add((Scal) v2));
				else if (b2)
					st.push(((Mat) v2).add((Scal) v1));
				else
					st.push(((Scal) v2).add((Scal) v1));
				break;
			}
			case "-": {
				if (st.size() < 2)
					break;
				MathOb<?> v1 = st.pop();
				MathOb<?> v2 = st.pop();
				boolean b1 = v1.getType().equals("Mat");
				boolean b2 = v2.getType().equals("Mat");
				if (b1)
					if (b2)
						st.push(((Mat) v1).sub((Mat) v2));
					else
						st.push(((Mat) v1).sub((Scal) v2));
				else if (b2)
					st.push(((Mat) v2).sub((Scal) v1));
				else
					st.push(((Scal) v2).sub((Scal) v1));
				break;
			}
			case "t": {
				MathOb<?> v = st.pop();
				boolean b = v.getType().equals("Mat");
				if (b)
					st.push(((Mat) v).t());
				else
					st.push(v);
				break;
			}
			case "tr": {
				MathOb<?> v = st.pop();
				boolean b = v.getType().equals("Mat");
				if (b)
					st.push(((Mat) v).tr());
				else
					st.push(v);
				break;
			}
			default:
				if (mem.containsKey(s))
					st.push(mem.get(s));
				else if (!st.isEmpty())
					mem.put(s, st.peek());
			}
		System.out.println(stringStack());
	}

	MathOb<?> parse(String exp) {
		return null;
	}

	String stringStack() {
		if (st.isEmpty())
			return "| Stack Empty";
		List<String[]> items = new ArrayList<>();
		int size = 0;
		for (MathOb<?> s : st) {
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
