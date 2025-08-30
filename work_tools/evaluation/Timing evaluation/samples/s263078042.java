import java.util.*;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt(), m = sc.nextInt();
		List<List<Long>> list = new ArrayList<List<Long>>();
		for (int i = 0; i < n; ++i) {
			long x = sc.nextLong();
			long y = sc.nextLong();
			long z = sc.nextLong();
			List<Long> tmp = new ArrayList<Long>();
			tmp.addAll(Arrays.asList(x, y, z));
			list.add(tmp);
		}
		sc.close();
		long ans = 0;
		int d[][] = { { -1, -1, -1 }, { -1, -1, 1 }, { -1, 1, -1 }, { -1, 1, 1 }, { 1, -1, -1 }, { 1, -1, 1 },
				{ 1, 1, -1 }, { 1, 1, 1 } };
		for (int i = 0; i < 8; ++i) {
			long tmp[] = new long[n];
			for (int j = 0; j < n; ++j) {
				for (int k = 0; k < list.get(j).size(); ++k) {
					tmp[j] += d[i][k] * list.get(j).get(k);
				}
			}
			Arrays.sort(tmp);
			long sum = 0;
			for(int j = 0; j < m; ++j) 
				sum += tmp[n - 1 - j];
			ans = Math.max(ans,  sum);
		}
		System.out.println(ans);
	}

}
