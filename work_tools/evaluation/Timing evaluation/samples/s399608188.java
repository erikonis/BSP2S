import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int N = Integer.parseInt(sc.next());
		int K = Integer.parseInt(sc.next());
		long[] v = new long[N];
		for (int i = 0; i < N; i++) {
			v[i] = Long.parseLong(sc.next());
		}
		sc.close();

		long ans = 0;
		int OP = Math.min(K, N);
		for (int a = 0; a <= OP; a++) {
			long sa = 0;
			ArrayList<Long> alist = new ArrayList<Long>();
			for (int i = 0; i < a; i++) {
				sa += v[i];
				if (v[i] < 0) {
					alist.add(v[i]);
				}
			}
			for (int b = 0; b <= OP - a; b++) {
				long sb = sa;
				ArrayList<Long> blist = new ArrayList<Long>();
				for(long n: alist){
					blist.add(n);
				}
				for (int i = N - 1; i > N - 1 - b; i--) {
					sb += v[i];
					if (v[i] < 0) {
						blist.add(v[i]);
					}
				}

				Collections.sort(blist);
				int pop = K - (a + b);
				for (long n : blist) {
					if (pop == 0) {
						break;
					}
					sb += Math.abs(n);
					pop--;
				}
				ans = Math.max(ans, sb);
			}
		}

		System.out.println(ans);

	}

}