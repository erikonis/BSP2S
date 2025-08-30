import java.util.*;

public class Main {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int N, finalRows, k = 0, sum, count;
		List<Integer> b = new ArrayList<Integer>();
		L0: while ((N = scan.nextInt()) != 0) {
			b.clear();
			sum = 0;
			for (int i = 0; i < N; i++)
				b.add(scan.nextInt());
			for (int val : b)
				sum += val;
			L1: if (true) {
				for (int i = 1; i <= 100; i++) {
					k = i * (i + 1) / 2;
					if (sum == k) {
						finalRows = i;
						break L1;
					}
				}
				System.out.println("-1");
				continue L0;
			}
			List<Integer> tri = new ArrayList<Integer>();
			for (int i = 0; i < finalRows; i++)
				tri.add(i + 1);
			count = 0;
			while (!tri.equals(b)) {
				if (count > 10000) {
					System.out.println("-1");
					continue L0;
				}
				for (int i = 0; i < b.size(); i++)
					b.set(i, b.get(i) - 1);
				b.add(b.size());
				count++;
				while (b.remove(new Integer(0)));
			}
			System.out.println(count);
			continue L0;
		}
	}
}