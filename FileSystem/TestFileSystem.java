import java.util.List;

public class TestFileSystem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileSystem fs = new FileSystem();
		fs.mkdir("/dir/");
		fs.mkdir("/dir/");
		System.out.println(fs.count() + " expected 1");
		fs.createFile("/dir/a");
		fs.createFile("/dir/a");
		fs.createFile("/dir/e");
		fs.createFile("/dir/d");
		fs.createFile("/dir/b");
		fs.createFile("/dir/c");
		System.out.println(fs.count() + " expected 6");
		fs.createFile("/file");
		System.out.println(fs.count() + " expected 7");
		
		List<Entry> list = fs.list("/");
		for (Entry e : list) {
			System.out.print(e.getName() + "  ");
		}
		System.out.println(" expected 2");
		
		list = fs.list("/dir/");
		for (Entry e : list) {
			System.out.print(e.getName() + "  ");
		}
		System.out.println(" expected 5");
		
		fs.delete("/file");
		System.out.println(fs.count() + " expected 6");
		
		list = fs.list("/");
		for (Entry e : list) {
			System.out.print(e.getName() + "  ");
		}
		System.out.println();
		
		fs.delete("/dir/");
		System.out.println(fs.count() + " expected 0");
	}
}
