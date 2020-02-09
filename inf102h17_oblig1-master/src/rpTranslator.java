package oblig;

import java.util.Scanner;
import java.util.Stack;

public class rpTranslator {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String string = in.nextLine();
		in.close();

		System.out.println(interpret(string));
	}

	private static String interpret(String string) {

		Stack<String> stack = new Stack<String>();
		String[] parts = string.split(" ");

		for (int i = 0; i < parts.length; i++) {
			String last;
			String first;
			String temp;

			if (parts[i].equals("+") || parts[i].equals("-") || parts[i].equals("/") || parts[i].equals("*")) {
				last = stack.pop();
				first = stack.pop();
				temp = "(" + first + " " + parts[i] + " " + last + ")";
				stack.push(temp);
			}

			else {
				stack.push(parts[i]);
			}

		}
		return stack.pop();

	}
}
