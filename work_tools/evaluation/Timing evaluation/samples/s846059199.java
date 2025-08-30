import java.util.Scanner;

public class Main {
	static long cnt=0;
	public static void Merge(int a[],int start,int end){
		if (start>=end) return;
		int center=(start+end)/2;
		Merge(a,start,center);
		Merge(a,center+1,end);
		int n1=center-start+1;
		int n2=end-center;
		int[] L=new int[n1];
		int[] R=new int[n2];
		for (int i=0;i<n1;i++){L[i]=a[start+i];}
		for (int i=0;i<n2;i++){R[i]=a[center+1+i];}
		int i=0; int j=0;
		for (int k=start;k<=end;k++){
			if (i==n1) a[k]=R[j++];
			else if (j==n2) a[k]=L[i++];
			else if (L[i]<=R[j]) a[k]=L[i++];
			else {a[k]=R[j++]; cnt+=n1-i;}
		}
	}
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
	    int n = in.nextInt();
	    int[] A = new int[n];
	    for (int i = 0; i<n; i++) {
	      A[i] = in.nextInt();
	    }
		Merge(A,0,n-1);
		System.out.println(cnt);
	}
}