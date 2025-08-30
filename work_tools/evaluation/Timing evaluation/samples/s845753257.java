import java.util.*;

public class Main {

	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int a[] = new int[n];
		boolean isoddcount = false;
		long ans = 0;
		for (int i = 0; i < n; ++i) {
			a[i] = sc.nextInt();
			if (a[i] < 0) {
				isoddcount = (!isoddcount);
				a[i] = -a[i];
			}
			ans += (long)a[i];
		}
		Arrays.sort(a);
		sc.close();
		if(isoddcount){
			ans -= 2 * (long)a[0];
		}
		System.out.println(ans);
	}
}
