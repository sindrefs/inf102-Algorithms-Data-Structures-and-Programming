package connectingToilets;

import graphics.Svg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.UF;

/**
 * Created by knutandersstokke on 16 16.10.2017.
 */
public class ConnectingToilets {

	private final static String TOILET_FILE = "connectingToilets/bergen_toilet_map.txt";

	public static void main(String[] args) {
		Set<Toilet> toilets = readToiletsFromFile(TOILET_FILE);
		ToiletMap mapOfToilets = new ToiletMap(toilets, connectToilets(toilets));
		Svg.runSVG(Svg.buildSvgFromScienceEmployees(mapOfToilets));
	}

	public static Set<Toilet> readToiletsFromFile(String toiletFile) {
		List<String> lines = readLines(TOILET_FILE);
		if (lines == null) {
			System.out.print("An error ocurred trying to read " + TOILET_FILE + ". Check that the file exist.");
		}
		return lines.stream().map(ConnectingToilets::lineToToilet).collect(Collectors.toSet());

	}

	public static Set<Edge> connectToilets(Set<Toilet> toilets) {
		Set<Edge> allEdges = new HashSet<>();
		List<Toilet> toiletsArray = new ArrayList<Toilet>();

		// Adding all possible connections
		for (Toilet toiletA : toilets) {
			toiletsArray.add(toiletA);
			for (Toilet toiletB : toilets) {
				double dist = Math.sqrt(
						Math.pow(toiletA.getX() - toiletB.getX(), 2) + Math.pow(toiletA.getY() - toiletB.getY(), 2));
				allEdges.add(new Edge(toiletA, toiletB, dist));
			}
		}

		Set<Edge> mst = new HashSet<Edge>();
		MinPQ<Edge> pq = new MinPQ<Edge>();

		for (Edge edge : allEdges)
			pq.insert(edge);

		UF uf = new UF(toilets.size());

		// Finding best connections and adding them to mst
		while (!pq.isEmpty() && mst.size() < toilets.size() - 1) {
			Edge e = pq.delMin();
			int toiletA = toiletsArray.indexOf(e.getToiletA());
			int toiletB = toiletsArray.indexOf(e.getToiletB());

			if (!uf.connected(toiletA, toiletB)) {
				uf.union(toiletA, toiletB);
				mst.add(e);
			}

		}

		return mst;
	}

	private static Toilet lineToToilet(String line) {
		String[] fields = line.split(";");
		String name = fields[0];
		double x = Double.parseDouble(fields[1]);
		double y = Double.parseDouble(fields[2]);
		return new Toilet(name, x, y);
	}

	private static List<String> readLines(String fileName) {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		try (InputStream resource = classloader.getResourceAsStream(fileName)) {
			if (resource == null) {
				System.out.println("File is missing!");
				return null;
			}
			return new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines()
					.collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
