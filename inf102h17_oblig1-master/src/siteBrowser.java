package oblig;

import java.util.Scanner;
import java.util.Stack;

public class siteBrowser {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		// int N = 11;

		int N = in.nextInt();
		String[] list = new String[N];

		/*
		 * list[0] = "Facebook"; list[1] = "Twitter"; list[2] = "Google";
		 * list[3] = "*back*"; list[4] = "*back*"; list[5] = "*forward*";
		 * list[6] = "YouTube"; list[7] = "*forward*"; list[8] = "LinkedIn";
		 * list[9] = "*back*"; list[10] = "*back*";
		 */

		for (int i = 0; i < N; i++) {
			list[i] = in.nextLine();
		}

		Stack<String> back = new Stack<String>();
		Stack<String> front = new Stack<String>();
		String current = null;
		for (int i = 0; i < N; i++) {
			if (list[i].equals("*back*")) {
				if (back.empty())
					System.out.println("[Warning: first website]");
				else {
					// Remember this site in front, and set the previous site as
					// current
					front.push(current);
					current = back.pop();
					System.out.println(current);

				}
			} else if (list[i].equals("*forward*")) {
				if (front.isEmpty()) {
					System.out.println("[Warning: last website]");
				} else {
					// Remember this site in back, and set the forward site as
					// current
					back.push(current);
					current = front.pop();
					System.out.println(current);
				}

			} else {
				if (current != null)
					// Current from last iteration
					back.push(current);

				// New current is initiated
				current = list[i];
				front.clear();
				System.out.println(current);
			}
		}

	}

}
