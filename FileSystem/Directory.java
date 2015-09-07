import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Directory extends Entry {
	protected List<Entry> contents;

	public Directory(String n, Directory p) {
		super(n, p);
		assert p == this;
		contents = new ArrayList<Entry>();
	}

	protected List<Entry> getContents() {
		lastAccessedTime = System.currentTimeMillis();
		return contents;
	}
	
	public int size() {
		int size = 0;
		for (Entry e : contents) {
			size += e.size();
		}
		return size;
	}

	public int numberOfFiles() {
		int count = 0;
		for (Entry e : contents) {
			if (e instanceof Directory) {
				count++; // Directory counts as a file
				Directory d = (Directory) e;
				count += d.numberOfFiles();
			} else if (e instanceof File) {
				count++;
			}
		}
		return count;
	}

	public boolean deleteEntry(Entry entry) {
		lastUpdatedTime = System.currentTimeMillis();
		return contents.remove(entry);
	}

	public void addEntry(Entry entry) {
		lastUpdatedTime = System.currentTimeMillis();
		binaryInsert(contents, entry);
	}
	
	/**************************** helper methods ****************************/
	
	private void binaryInsert(List<Entry> list, Entry targetEntry) {
		assert list != null;
		
		if (list.size() == 0) {
			list.add(targetEntry);
			return;
		}
		
		int left = 0;
		int right = list.size() - 1;
		while (left <= right) {
			int mid = left + (right - left) / 2;
			int cmp = list.get(mid).name.compareTo(targetEntry.name);
			if (cmp == 0) {
				list.add(mid + 1, targetEntry);
				return;
			} else if (cmp < 0) {
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		
		list.add(left, targetEntry);
	}

}
