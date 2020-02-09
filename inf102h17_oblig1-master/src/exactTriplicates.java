package oblig;

import edu.princeton.cs.algs4.Merge;

public class exactTriplicates {

	public static void main(String[] args) {

		// String[] list1 = { "Åshild", "Sigurd", "Sigurd" };
		// String[] list2 = { "Tore", "Aslak", "Trine" };
		// String[] list3 = { "Åshild", "Tora", "Luke" };
		// String[] list4 = { "Åshild", "My", "Magnus" };

		String[] list1 = { "D", "D", "D", "D" };
		String[] list2 = { "E", "E", "E", "E" };
		String[] list3 = { "C", "Per", "C", "E" };
		String[] list4 = { "C", "C", "Per", "E" };

		Merge.sort(list1);
		Merge.sort(list2);
		Merge.sort(list3);
		Merge.sort(list4);

		System.out.println(threeInstances(list1, list2, list3, list4));
	}

	private static String threeInstances(String[] list1, String[] list2, String[] list3, String[] list4) {
		int a = 0;
		int b = 0;
		boolean last1 = false;
		boolean last2 = false;
		while ((a < list1.length || b < list2.length)) {

			// System.out.println("a: " + a + ", b: " + b + ", last1: " + last1
			// + ", last2: " + last2);

			// If first letter in element from list1 comes first in the Alphabet
			if ((list1[a].compareTo(list2[b]) <= 0 || last2)) {
				if (binarySearch(list1[a], list2) + binarySearch(list1[a], list3) + binarySearch(list1[a], list4) == 2)
					return list1[a];
				else {
					if (a + 1 < list1.length)
						a++;
					else
						last1 = true;
				}
			}
			// If first letter in element from list2 comes first in the Alphabet
			if ((list1[a].compareTo(list2[b]) > 0 || last1)) {
				if (binarySearch(list2[b], list1) + binarySearch(list2[b], list3) + binarySearch(list2[b], list4) == 2)
					return list2[b];
				else {
					if (b + 1 < list2.length)
						b++;
					else
						last2 = true;
				}
			}

		}
		return null;

	}

	// Custom binarysearch returns 0 or 1 depending on results
	private static int binarySearch(String string, String[] list) {
		int low = 0;
		int mid;
		int high = (list.length - 1);

		while (low <= high) {
			mid = (low + high) / 2;

			if (list[mid].compareTo(string) < 0)
				low = mid + 1;
			else if (list[mid].compareTo(string) > 0)
				high = mid - 1;
			else
				return 1;
		}
		return 0;
	}

}
