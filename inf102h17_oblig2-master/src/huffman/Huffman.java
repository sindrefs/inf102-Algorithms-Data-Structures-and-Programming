package huffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.ST;

public class Huffman {

	private static final int OCCURRENCES = 1500;
	private static final int WORD_LENGTH = 8;

	private static class Node implements Comparable<Node> {
		private String key;
		private int value;
		private Node left;
		private Node right;

		public Node(String key, int value, Node left, Node right) {
			this.key = key;
			this.value = value;
			this.left = left;
			this.right = right;
		}

		public boolean isLeaf() {
			return (left == null) && (right == null);
		}

		@Override
		public int compareTo(Node other) {
			return Integer.compare(this.value, other.value);

		}

	}

	public static void main(String[] args) {

		try {
			encode("src/leipzig1m.txt");
		} catch (FileNotFoundException f) {
			System.out.println("File not found");
		}

		try {
			decode("output.txt.cmp");

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}

	}

	private static void encode(String fileName) throws FileNotFoundException {

		System.out.println("Reading from file to st started");

		File file = new File(fileName);
		Scanner scanner = new Scanner(file);
		// Initiate output file and writer
		String outfilename = "output.txt.cmp";
		File outfile = new File(outfilename);
		Out out = new Out(outfilename);
		// Reading to from file to st

		ST<String, Integer> st = new ST<String, Integer>();

		while (scanner.hasNextLine()) {
			String str = scanner.nextLine();
			// System.out.print("\nLinje: " + str);

			String[] parts = str.split(" ");

			for (String string : parts) {
				// System.out.println("\nString: " + string);
				if (!st.contains(string))
					st.put(string, 1);
				else
					st.put(string, st.get(string) + 1);
			}
		}

		scanner.close();

		System.out.println("-completed");

		System.out.println("Writing from st to Huffman pq started");
		MinPQ<Node> pq = new MinPQ<Node>();
		for (String entry : st) {
			if (st.get(entry) > OCCURRENCES && entry.length() > WORD_LENGTH)
				pq.insert(new Node(entry, st.get(entry), null, null));
		}

		while (pq.size() > 1) {
			Node left = pq.delMin();
			Node right = pq.delMin();
			Node parent = new Node("parent", left.value + right.value, left, right);
			pq.insert(parent);
		}
		System.out.println("-completed");
		System.out.println("Assigning binary values from three to HashMap started");
		HashMap<String, String> binaryRep = new HashMap<String, String>();

		nodeBinary(pq.min(), "", binaryRep);

		System.out.println("-completed");

		System.out.println("Going through file and replacing words with binary started");

		Map<String, String> map = binaryRep;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			out.print(entry.getKey() + "=" + entry.getValue() + "/");
		}

		out.println();
		scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] parts = line.split(" ");

			int i = 1;
			for (String string : parts) {
				String newStr = "";
				if (binaryRep.containsKey(string)) {
					newStr = binaryRep.get(string);
				} else
					newStr = string;

				if (i++ != parts.length)
					out.print(newStr + " ");
				else
					out.print(newStr);

			}
			out.println();
		}
		scanner.close();
		System.out.println("-completed all");

	}

	private static void decode(String fileName) throws FileNotFoundException {
		System.out.println("Decrypting started");
		File file = new File(fileName);
		Scanner scanner = new Scanner(file);

		String line1 = scanner.nextLine();
		String[] line1_parts = line1.split("/");
		HashMap<String, String> binaryRep = new HashMap<String, String>();

		for (int i = 0; i < line1_parts.length; i++) {
			String[] split = line1_parts[i].split("=");
			// System.out.println(line1_parts[i]);
			binaryRep.put(split[1], split[0]);
		}

		String outfilename = "outputdecrypted.txt";
		File outfile = new File(outfilename);
		Out out = new Out(outfilename);

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] parts = line.split(" ");
			int i = 1;
			for (String string : parts) {
				String newStr = "";
				if (binaryRep.containsKey(string)) {
					newStr = binaryRep.get(string);
				} else
					newStr = string;
				if (i++ != parts.length)
					out.print(newStr + " ");
				else
					out.print(newStr);
			}
		}

		scanner.close();
		System.out.println("-completed");

	}

	private static void nodeBinary(Node node, String parent, HashMap<String, String> map) {
		String string = "";
		string += parent;

		if (node.isLeaf()) {
			int occurrences = node.value;
			int difference = node.key.length() - string.length();
			int cost = node.key.length() + string.length() + 2;
			if (occurrences * difference > cost)
				map.put(node.key, string);
		}

		if (node.left != null)
			nodeBinary(node.left, string + "0", map);
		if (node.right != null)
			nodeBinary(node.right, string + "1", map);

	}
}