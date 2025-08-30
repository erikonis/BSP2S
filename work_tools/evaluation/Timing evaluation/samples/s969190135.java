import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {

		Scanner scan = new Scanner(System.in);
		// Scanner scan = new Scanner(new File("c:\\0520-input.txt"));

		while (scan.hasNext()) {
			int m = scan.nextInt();

			if (m == 0)
				break;
			Cnstl c = new Cnstl(m);
			for (int i = 0; i < m; i++)
				c.add(i, scan.nextInt(), scan.nextInt());

			int n = scan.nextInt();
			Map map = new Map(n);
			for (int i = 0; i < n; i++)
				map.add(scan.nextInt(), scan.nextInt());

			map.sort();

			Map.Point v = map.search(c);
			System.out.println(v.x + " " + v.y);
		}
		scan.close();
		System.exit(0);
	}
}

class Map {
	class Point {
		int x;
		int y;

		public Point(int _x, int _y) {
			x = _x;
			y = _y;
		}
	}

	class Pcomp implements Comparator<Point> {
		@Override
		public int compare(Point o1, Point o2) {
			if (o1.x > o2.x)
				return 1;
			else if (o1.x == o2.x)
				return 0;
			else
				return -1;
		}

	}

	List<Point> p = new ArrayList<Point>();

	public Map(int n) {
	}

	public Point search(Cnstl c) {
		for (int i = 0; i < p.size(); i++) {
			int dx = p.get(i).x - c.getX(0);
			int dy = p.get(i).y - c.getY(0);
			if (this.match(c, dx, dy)) {
				return (new Point(dx, dy));
			}
		}
		return null;
	}

	private boolean match(Cnstl c, int dx, int dy) {
		for (int i = 1; i < c.size(); i++)
			if (c.getX(i) + dx < 0 || c.getX(i) + dx > 1000000 || c.getY(i) + dy < 0 || c.getY(i) + dy > 1000000)
				return false;
		for (int i = 1; i < c.size(); i++)
			if (!this.exist(c.getX(i) + dx, c.getY(i) + dy))
				return false;
		return true;
	}

	private boolean exist(int x, int y) {
/*		int si = p.size() / 2;
		int sd = si / 2;
		for (; sd > 5; sd /= 2)
			if (p.get(si).x > x)
				si -= sd;
			else
				si += sd;
*/
		for (int i = 0; i < p.size(); i++)
			if (p.get(i).x == x && p.get(i).y == y)
				return true;
		return false;
	}

	public void sort() {
		Collections.sort(p, new Pcomp());
	}

	public void add(int x, int y) {
		p.add(new Point(x, y));
	}

}

class Cnstl {
	private int[] x;
	private int[] y;

	public Cnstl(int m) {
		x = new int[m];
		y = new int[m];
	}

	public int size() {
		return x.length;
	}

	public int getX(int i) {
		return x[i];
	}

	public int getY(int i) {
		return y[i];
	}

	public void add(int i, int _x, int _y) {
		x[i] = _x;
		y[i] = _y;
	}

}