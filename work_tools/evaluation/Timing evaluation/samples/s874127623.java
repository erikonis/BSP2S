import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		char[] a = sc.next().toCharArray();
		sc.close();

		int[] x = new int[a.length - 1];
		boolean one = false;
		for ( int j = 0 ; j < a.length - 1 ; j++ ) {
			x[j] = Math.abs((int)a[j + 1] - (int)a[j]);
			if ( x[j] == 1 ) {
				one = true;
			}
		}

		int ans = 0;
		if ( one ) {
			int n = x.length - 1;
			boolean odd = false;
			for ( int r = 0 ; r < x.length ; r++ ) {
				if ( x[r] == 1 && n == ( n | (n - r)) ) {
					odd = !odd;
				}
			}
			ans = odd ? 1 : 0;
		} else {
			int n = x.length - 1;
			boolean odd = false;
			for ( int r = 0 ; r < x.length ; r++ ) {
				if ( x[r] == 2 && n == ( n | (n - r)) ) {
					odd = !odd;
				}
			}
			ans = odd ? 2 : 0;
		}
		System.out.println(ans);
	}
}
