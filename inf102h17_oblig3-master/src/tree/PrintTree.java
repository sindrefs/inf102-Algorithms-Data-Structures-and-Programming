package tree;

import edu.princeton.cs.algs4.In;
import java.util.*;

public class PrintTree {

	// Custom node implementation. Possible with multiple children
	private static class Node implements Comparable<Node> {
		private String key;
		private List<Node> children = new ArrayList<Node>();

		public Node(String key) {
			this.key = key;
		}

		public String getKey() {
			return this.key;
		}

		public void addChild(String key) {
			children.add(new Node(key));

		}

		public List<Node> getChildren() {
			return this.children;
		}

		@Override
		public int compareTo(Node other) {
			return key.compareTo(other.getKey());
		}

	}

	private final static int INDENTATION_SIZE = 2;

	public static void main(String[] args) {
		String inputFile = "folders.txt";
		String inputContent = new In(inputFile).readAll();
		System.out.println(formatStringToTree(inputContent));
	}

	public static String formatStringToTree(String inputContent) {
		String[] lines = inputContent.split("\\r?\\n");

		String[] fileNames = lines[1].split(" ");
		Node root = new Node("root");

		for (int i = 2; i < lines.length; i++) {
			String[] ints = lines[i].split(" ");

			Node node = findNode(root, fileNames[Integer.parseInt(ints[0])]);

			for (int j = 2; j < ints.length; j++)
				node.addChild(fileNames[Integer.parseInt(ints[j])]);

		}
		printTree(root, "");

		return "";
	}

	private static void printTree(Node node, String indent) {
		System.out.println(indent + "'-" + node.getKey());
		for (Node child : node.getChildren()) {
			// Extra space
			String extraSpace = "";
			for (int i = 0; i < INDENTATION_SIZE; i++) {
				extraSpace += " ";
			}
			printTree(child, indent + extraSpace);
		}
	}

	private static Node findNode(Node node, String key) {
		if (key.equals(node.getKey()))
			return node;

		List<Node> children = node.getChildren();
		Node res = null;
		for (int i = 0; res == null && i < children.size(); i++) {
			res = findNode(children.get(i), key);
		}

		return res;
	}
}
