package minimiseDisks;

import java.util.ArrayList;
import java.util.Collections;

import edu.princeton.cs.algs4.In;

public class DiskAdmin {

	private static int CAPACITY = 1000; // mb

	public static void main(String[] args) {

		ArrayList<Disk> disks1 = new ArrayList<Disk>();
		ArrayList<Disk> disks2 = new ArrayList<Disk>();

		disks1.add(new Disk(CAPACITY));
		disks2.add(new Disk(CAPACITY));

		String fileName = "disk.txt";
		In infile = new In(fileName);
		ArrayList<Integer> nrList = new ArrayList<Integer>();
		while (!infile.isEmpty()) {
			int nr = Integer.parseInt(infile.readString());
			nrList.add(nr);
		}

		// Solution one
		System.out.println("Solution one:\n");
		addToBest(disks1, nrList);
		printSolution(disks1);

		// Solution two
		System.out.println("\n\nSolution two:\n");
		Collections.sort(nrList, Collections.reverseOrder());
		addToBest(disks2, nrList);
		printSolution(disks2);
	}

	private static void printSolution(ArrayList<Disk> disks) {
		for (int i = 0; i < disks.size(); i++)
			System.out.println(
					"Disk " + (i + 1) + " med " + disks.get(i).allFilesSize() + " MB: " + disks.get(i).toString());
		System.out.println("We needed " + disks.size() + " disks.");
	}

	private static void addToBest(ArrayList<Disk> disks, ArrayList<Integer> nrList) {
		for (Integer index : nrList) {
			int bestDisk = bestDiskIndex(disks);
			if (disks.get(bestDisk).availableSpace() < index) {
				disks.add(new Disk(CAPACITY));
				disks.get(disks.size() - 1).add(index);
			} else
				disks.get(bestDisk).add(index);
		}
	}

	private static int bestDiskIndex(ArrayList<Disk> disks) {
		int emptyist = 0;
		for (int i = 0; i < disks.size(); i++) {
			if (disks.get(emptyist).availableSpace() < disks.get(i).availableSpace())
				emptyist = i;
		}
		return emptyist;

	}

}
