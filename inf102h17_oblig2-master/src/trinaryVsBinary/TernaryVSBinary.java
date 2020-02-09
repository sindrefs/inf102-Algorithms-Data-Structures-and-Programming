package trinaryVsBinary;

import java.util.ArrayList;
import java.util.Random;

import edu.princeton.cs.algs4.In;

public class TernaryVSBinary {

	public static void main(String[] args) {

		UTST<String> utst = new UTST<String>();
		UBST<String, Integer> ubst = new UBST<String, Integer>();
		ArrayList<String> string = new ArrayList<String>();

		String fileName = "tinyTale.txt";
		In infile = new In(fileName);

		while (!infile.isEmpty()) {
			String key = infile.readString();
			string.add(key);
			utst.put(key);
			ubst.put(key, 0);
		}

		for (String str : string) {
			utst.get(str);
			ubst.get(str);
		}

		System.out.println(fileName + ":\nUTST: " + utst.getCnt() + "\nUBST: " + ubst.getCnt());

	}

}
