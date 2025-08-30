

import java.util.Scanner;

public class Main {

	private static Scanner in;
	
	private static int n;
	private static int[] h;
	private static int[] v;
	private static final int MOD = (int)1e9+7;
	private static long[] seg;
	
	public static void main(String[] args) {
		in = new Scanner(System.in);
		n = in.nextInt();
		h =  new int[n];
		v =  new int[n];
		for(int i=0; i<n; i++) {
			h[i] = in.nextInt();
		}

		for(int i=0; i<n; i++) {
			v[i] = in.nextInt();
		}
		
		int m = 4*(int)1e6;
		seg = new long[m];
		
		for(int i=n-1; i>=0; i--) {
			long value = query(h[i]+1, (int)1e6, 0, 4*n-1, 0);
			
			update(h[i], value+v[i], 0, 4*n-1, 0);
		}
		
		System.out.println(query(0, (int)1e6, 0, 4*n-1, 0));
		
	}
	
	private static long query(int qlow, int qhigh, int low, int high, int pos) {
		if (qlow>high || low>qhigh) {
			return -(long)1e15;
		} else if (qlow<=low && qhigh>=high) {
			return seg[pos];
		} else {
			int mid = (low+high)/2;
			return Math.max(query(qlow, qhigh, low, mid, 2*pos+1), query(qlow, qhigh, mid+1, high, 2*pos+2));
		}
	}
	
	private static void update(int index, long v, int low, int high, int pos) {
		if (low==high) {
			seg[pos] = Math.max(seg[pos], v);
			return;
		}
		
		int mid = (low+high)/2;
		if (index<=mid) {
			update(index, v, low, mid, 2*pos+1);
		} else {
			update(index, v, mid+1, high, 2*pos+2);
		}
		
		seg[pos] = Math.max(seg[2*pos+1], seg[2*pos+2]);
		
		
	}
	
	

}
