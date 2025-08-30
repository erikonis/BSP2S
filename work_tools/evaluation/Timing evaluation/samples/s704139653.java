import java.util.*;
public class Main {

	static void solve() {
		Scanner sc = new Scanner(System.in);
		int[] a = {sc.nextInt(),sc.nextInt(),sc.nextInt()};
		int k = sc.nextInt(), t = 0;
		Arrays.sort(a);
		while (k-->0) a[a.length-1]*=2;
		for (int b : a) t+=b;
		System.out.println(t);
		sc.close();			
	}

	public static void main(String args[]) {
		solve();
	}

}