package oblig;

import java.util.Arrays;
import java.util.Random;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.BinarySearch;

public class profitSorting {

	final static int N = (int) Math.pow(10, 6);
	final static int INT_RANGE = (int) (Math.pow(2, 31) - 1);
	final static Random rand = new Random();
	final static int S = 450;

	public static void main(String[] args) {

		int[] list = generateList();

		// Linear search
		Stopwatch klokkeLinear = new Stopwatch();
		for (int i = 0; i < S; i++) {
			int findThis = rand.nextInt(INT_RANGE);
			for (int j = 0; j < N; j++) {
				if (list[j] == findThis) {
					System.out.println("Element funnet ved lineært søk, plassering: " + j);
					break;
				}
			}
		}
		Double timeLinear = klokkeLinear.elapsedTime();

		// Binary search
		Stopwatch klokkeBinary = new Stopwatch();

		Arrays.sort(list);
		for (int i = 0; i < S; i++) {
			int findThis = rand.nextInt(INT_RANGE);
			int index = BinarySearch.indexOf(list, findThis);
			if (index != -1)
				System.out.println("Element funnet ved binært søk, plassering: " + index);
		}

		Double timeBinary = klokkeBinary.elapsedTime();

		System.out.println(
				"Kjørt " + S + " ganger, linear brukte " + timeLinear + " sek. Binary brukte " + timeBinary + " sek.");

	}

	private static int[] generateList() {
		int[] list = new int[N];
		for (int i = 0; i < N; i++) {
			list[i] = rand.nextInt(INT_RANGE);
		}
		return list;
	}
}
