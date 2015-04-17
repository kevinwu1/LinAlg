package math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class MathT {
	public static void main(String[] args) {
		System.out.println(Arrays.toString(factor(12)));
		System.out.println(GCF(5, 10));
	}

	public static int GCF(int x, int y) {
		x = Math.abs(x);
		y = Math.abs(y);
		int o = 1;
		for (int i = 2; i <= x && i <= y; i++)
			while (x % i == 0 && y % i == 0) {
				o *= i;
				x /= i;
				y /= i;
			}
		return o;
	}

	public static int[] factor(int x) {
		List<Integer> o = new ArrayList<>();
		for (int i = 2; i * i <= x; i++) {
			while (x % i == 0) {
				x /= i;
				o.add(i);
			}
		}
		if (x != 1)
			o.add(x);
		int[] out = new int[o.size()];
		for (int i = 0; i < o.size(); i++) {
			out[i] = o.get(i);
		}
		return out;
	}
}
