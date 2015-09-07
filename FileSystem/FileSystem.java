import java.util.ArrayList;
import java.util.List;

enum EntryType {
	DIRECTORY, FILE;
}

public class FileSystem {
	private final Directory root;
	
	public FileSystem() {
		root = new Directory("/", null);
	}

	// resolve both path of directory or file
	// if the path cannot be resolved, return null
	public List<Entry> resolve(String path) {
		// TODO: write program to resolve path like “/foo/bar/baz”
		assert path != null;
		if (path.length() == 0 || path.charAt(0) != '/') return null;
		
		List<Entry> result = new ArrayList<>();
		List<Entry> curChildren;
		
		result.add(root);
		if (path.length() == 1) {
			return result;
		}

		path = path.substring(1);
		curChildren = root.getContents();
		
		int index = path.indexOf('/');
		while (index != -1) {
			String curDir = path.substring(0, index);
			int foundIndex = binarySearch(curChildren, curDir, EntryType.DIRECTORY);
			if (foundIndex == -1) return null;
			Directory foundEntry = (Directory)curChildren.get(foundIndex);
			result.add(foundEntry);
			curChildren = foundEntry.getContents();
			if (index == path.length() - 1) {
				return result;
			}
			path = path.substring(index + 1);
			index = path.indexOf('/');
		}
		
		int foundIndex = binarySearch(curChildren, path, EntryType.FILE);
		if (foundIndex == -1) return null;
		result.add(curChildren.get(foundIndex));
		return result;
	}
	
	// the path must be valid (able to resolve) and be a Directory path
	public List<Entry> list(String path) {
		// TODO: list all the immediate children of the directory specified by
		// the given path
		assert path != null && path.length() != 0 && path.charAt(path.length() - 1) == '/';

		List<Entry> entryChain = resolve(path);

		assert entryChain != null;
		
		Entry last = entryChain.get(entryChain.size() - 1);
				
		return ((Directory)last).getContents();		
	}

	// the parent directory of the target directory must exist
	// if the directory already exits, do nothing and return false
	public boolean mkdir(String path) {
		// TODO: create a new directory with the given path
		assert path != null && path.length() > 2 && path.charAt(0) == '/' 
				&& path.charAt(path.length() - 1) == '/';
		
		int index = path.substring(0, path.length() - 1).lastIndexOf('/');
		String parentPath = path.substring(0, index + 1);
		String newDirName = path.substring(index + 1, path.length() - 1);
		
		List<Entry> entryChain = resolve(parentPath);
		assert entryChain != null;
		
		Entry last = entryChain.get(entryChain.size() - 1);
		Directory lastDir = (Directory)last;
		List<Entry> lastDirContents = lastDir.getContents();
		
		int foundIndex = binarySearch(lastDirContents, newDirName, EntryType.DIRECTORY);
		if (foundIndex != -1) return false;
		
		lastDir.addEntry(new Directory(newDirName, lastDir));
		return true;
	}
	
	// the parent directory of the target file must exist
	// if the file already exits, do nothing and return false
	public boolean createFile(String path) {
		// TODO: create a new file with the given path
		assert path != null && path.length() > 1 && path.charAt(0) == '/' 
				&& path.charAt(path.length() - 1) != '/';
		
		int index = path.lastIndexOf('/');
		String parentPath = path.substring(0, index + 1);
		String newFileName = path.substring(index + 1, path.length());
		
		List<Entry> entryChain = resolve(parentPath);
		assert entryChain != null;
		
		Entry last = entryChain.get(entryChain.size() - 1);
		Directory lastDir = (Directory)last;
		List<Entry> lastDirContents = lastDir.getContents();
		
		int foundIndex = binarySearch(lastDirContents, newFileName, EntryType.FILE);
		if (foundIndex != -1) return false;
		
		lastDir.addEntry(new File(newFileName, lastDir, 0));
		return true;
	}

	public void delete(String path) {
		// TODO: delete the file/directory with the given path
		List<Entry> entryChain = resolve(path);
		assert entryChain != null;
		Entry last = entryChain.get(entryChain.size() - 1);
		last.delete();
	}

	public int count() {
		// TODO: return the total number of files/directories in the FileSystem
		return root.numberOfFiles();
	}
	
	/**************************** helper methods ****************************/
	
	private int binarySearch(List<Entry> list, String targetName, EntryType type) {
		assert list != null;
		
		if (list.size() == 0) return -1;
		
		int left = 0;
		int right = list.size() - 1;
		while (left <= right) {
			int mid = left + (right - left) / 2;
			int cmp = list.get(mid).name.compareTo(targetName);
			if (cmp == 0) {
				if (type == EntryType.DIRECTORY) {
					if (list.get(mid) instanceof Directory && list.get(mid).name.equals(targetName)) {
						return mid;
					}
					if (mid + 1 <= right && list.get(mid + 1) instanceof Directory && list.get(mid + 1).name.equals(targetName)) {
						return mid + 1;
					}
					if (mid - 1 >= left && list.get(mid - 1) instanceof Directory && list.get(mid - 1).name.equals(targetName)) {
						return mid - 1;
					}
					return -1;
				} else {
					if (list.get(mid) instanceof File && list.get(mid).name.equals(targetName)) {
						return mid;
					}
					if (mid + 1 <= right && list.get(mid + 1) instanceof File && list.get(mid + 1).name.equals(targetName)) {
						return mid + 1;
					}
					if (mid - 1 >= left && list.get(mid - 1) instanceof File && list.get(mid - 1).name.equals(targetName)) {
						return mid - 1;
					}
					return -1;
				}
			} else if (cmp < 0) {
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		
		return -1;
	}
}
