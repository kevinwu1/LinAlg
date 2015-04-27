package linAlg;

import java.util.Scanner;

public class Runner {
	public static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		Shell s = new Shell(scan);
		while (true)
			s.eval(scan.next());
	}
}
