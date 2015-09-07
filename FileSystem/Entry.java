abstract class Entry {
	protected Directory parent;
	protected String name;
	protected long createdTime;
	protected long lastUpdatedTime;
	protected long lastAccessedTime;

	public Entry(String n, Directory p) {
		assert n != null && n.length() != 0;
		name = n;
		parent = p;
		createdTime = System.currentTimeMillis();
	}

	public boolean delete() {
		if (parent == null) {
			return false;
		}
		return parent.deleteEntry(this);
	}

	public String getFullPath() {
		if (parent == null) {
			return name;
		} else {
			return parent.getFullPath() + "/" + name;
		}
	}
	
	public abstract int size();

	public long getCreationTime() {
		return createdTime;
	}

	public long getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public long getLastAccessedTime() {
		return lastAccessedTime;
	}

	public void changeName(String n) {
		name = n;
		lastUpdatedTime = System.currentTimeMillis();
	}

	public String getName() {
		return name;
	}
}
