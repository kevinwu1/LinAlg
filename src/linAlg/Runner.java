package linAlg;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Runner {
	public static void main(String[] args) {
		Mat A = new Mat(3, 2, 3, 0, -1, 2, 1, 1);
		Mat B = new Mat(2, 2, 4, -1, 0, 2);
		Mat C = new Mat(2, 3, 1, 4, 2, 3, 1, 5);
		Mat D = new Mat(3, 3, 1, 5, 2, -1, 0, 1, 3, 2, 4);
		Mat E = new Mat(3, 3, 6, 1, 3, -1, 1, 2, 4, 1, 3);
		// try {
		// System.out.println(A.t().mult(2).add(C));
		// System.out.println(D.t().sub(E.t()));
		// System.out.println((D.sub(E)).t());
		// System.out.println(B.t().add(C.t().mult(5)));
		// System.out.println(C.t().div(2).sub(A.div(4)));
		// System.out.println(B.sub(B.t()));
		// System.out.println(E.t().mult(2).sub(D.t().mult(3)));
		// System.out.println((E.t().mult(2).sub(D.t().mult(3))).t());
		// System.out.println(C.mult(D).mult(E));
		// System.out.println(C.mult((B.mult(A))));
		// System.out.println(D.mult(E.t()).tr());
		// System.out.println(B.mult(C).tr());
		// }
		// catch (Exception e) {
		// e.printStackTrace();
		// }
		Map<String, Mat> mats = new HashMap<>();
		mats.put("A", A);
		mats.put("B", B);
		mats.put("C", C);
		mats.put("D", D);
		mats.put("E", E);
		Shell s = new Shell(mats);
		try (Scanner scan = new Scanner(System.in)) {
			while (true)
				s.eval(scan.next());
		}
	}
}
