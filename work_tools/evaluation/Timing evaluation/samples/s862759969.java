
import java.util.*;
import java.io.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;

public class Main {

	int INF = 1 << 28;
	//long INF = 1L << 62;
	double EPS = 1e-10;

	void run() {
		Scanner sc = new Scanner(System.in);
		int[] cnt = new int[3];
		for(;sc.hasNext();) {
			int a[] = new int[3];
			for(int i=0;i<3;i++) a[i] = sc.nextInt();
			sort(a);
			int b = triangle(a);
			if(b<0) break;
			cnt[b]++;
		}
		for(;sc.hasNext();)sc.next();
		System.out.println((cnt[0]+cnt[1]+cnt[2]) + " " + cnt[1] + " " + cnt[0] + " " + cnt[2]);
	}
	
	int triangle(int[] a) {
		if(a[0]+a[1] <= a[2]) return -1;
		if(a[0]*a[0] + a[1]*a[1] > a[2]*a[2])  return 0;
		if(a[0]*a[0] + a[1]*a[1] == a[2]*a[2]) return 1;
		if(a[0]*a[0] + a[1]*a[1] < a[2]*a[2]) return 2;
		return -1;
	}

	void debug(Object... os) {
		System.err.println(Arrays.deepToString(os));
	}

	public static void main(String[] args) {
		new Main().run();
	}
}