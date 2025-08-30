

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {

	static Scanner scanner;
	static long[][] dp;
    static int ind=1;
	public static void main(String[] args) {
	    scanner = new Scanner(System.in);

	    int n=gi();
	    Integer[] A=new Integer[n];
	    Integer[] B=new Integer[n];
	    Integer[] C=new Integer[n];

        for (int i=0; i< n;i++) {
        	A[i]=gi();
        }
        for (int i=0; i< n;i++) {
        	B[i]=gi();
        }
        for (int i=0; i< n;i++) {
        	C[i]=gi();
        }

        Arrays.parallelSort(A, Collections.reverseOrder());
        Arrays.parallelSort(B, Collections.reverseOrder());
        Arrays.parallelSort(C, Collections.reverseOrder());
        long ans=0;
        long[] abb =new long[n];
        long[] bbc =new long[n];

        for (int i=0; i<n;i++) {
        	int l=-1;
        	int r=n;
        	int b=B[i];
        	while(r-l>1) {
        		int ind=(l+r)/2;
        		if(C[ind]<=b) {
        			r=ind;
        		} else {
        			l=ind;
        		}

        	}
        	bbc[i]=r;
        	if(i!=0)bbc[i]+=bbc[i-1];
        }

        for (int i=0; i<n;i++) {
        	int l=-1;
        	int r=n;
        	int a=A[i];
        	while(r-l>1) {
        		int ind=(l+r)/2;
        		if(B[ind]<=a) {
        			r=ind;
        		} else {
        			l=ind;
        		}
        	}
        	if(l>=0) ans+=bbc[l];
        }

        System.out.println(ans);
	}

	// 文字列として入力を取得
	public static String gs() {
		return scanner.next();
	}

	// intとして入力を取得
	public static int gi() {
		return Integer.parseInt(scanner.next());
	}

	// longとして入力を取得
	public static long gl() {
		return Long.parseLong(scanner.next());
	}

	// doubleとして入力を取得
	public static double gd() {
		return Double.parseDouble(scanner.next());
	}
}