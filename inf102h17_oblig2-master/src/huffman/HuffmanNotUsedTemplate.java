package huffman;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import edu.princeton.cs.algs4.In;

public class HuffmanNotUsedTemplate {
	
	public static void main(String[] args) {
		if(args.length < 1)
			throw new IllegalArgumentException("Please provide filename of file to be encoded");
		String inputFile = args[0];
		String content = new In(inputFile).readAll();
		if (inputFile.endsWith(".txt")) {
			encode(content, inputFile);
			System.out.println("Compression done");
		} else if (inputFile.endsWith(".cmp")) {
			System.out.println("decoded text:");
			System.out.println(decode(content));
		} else {
			throw new IllegalArgumentException("Please provide a .txt file or a compressed .cmp file");
		}
	}

	/*
	 * TODO: Replace all or parts of this code to use your huffman implementation to encode a file
	 */
	public static void encode(String content, String originalFileName) {
		try {
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(originalFileName + ".cmp"), "utf-8"));
			writer.write("****\n"+content);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * TODO: Replace all or parts of this code to use your huffman implementation to decode a file
	 */
	public static String decode(String content) {
		String[] lines = content.split("\n");
		StringBuilder builder = new StringBuilder();
		for(int i=1; i < lines.length; i++) {
			builder.append(lines[i] + "\n");
		}
		builder.deleteCharAt(builder.length() - 1); // NB! What if file contains empty lines at the end?
		return builder.toString();
	}

}
