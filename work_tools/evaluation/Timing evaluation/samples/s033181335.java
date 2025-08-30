import java.util.*;

public class Main {
	public static void main (String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int m = sc.nextInt();
		ArrayList<Integer> list = new ArrayList<>();
		HashSet<Integer> set = new HashSet<>();
		for (int i = 0; i < m; i++) {
			int a = sc.nextInt();
			int b = sc.nextInt();
			if (a == 1) {
				list.add(b);
			}
			if (b == n) {
				set.add(a);
			}
		}
		for (Integer x : list) {
			if (set.contains(x)) {
				System.out.println("POSSIBLE");
				return;
			}
		}
		System.out.println("IMPOSSIBLE");
	}
}
