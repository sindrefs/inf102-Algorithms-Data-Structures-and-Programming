package minimiseDisks;

import java.util.ArrayList;
import java.util.List;

public class Disk {

	int size;
	private List<Integer> files;

	public Disk(int size) {
		this.size = size;
		this.files = new ArrayList<Integer>();
	}

	public boolean add(Integer file) {
		if ((allFilesSize() + file) < size) {
			files.add(file);
			return true;
		} else
			return false;
	}

	public int availableSpace() {
		return size - allFilesSize();
	}

	public int size() {
		return size;
	}

	public int allFilesSize() {
		int tot = 0;
		for (int i = 0; i < files.size(); i++)
			tot += files.get(i);
		return tot;
	}

	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < files.size(); i++)
			str += "Fil " + i + ": " + files.get(i)+", ";
		return str;

	}
}
