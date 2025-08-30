import java.util.Scanner;

public class Main{
	public static int n;
	public static int []A = new int[50];
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		n = scan.nextInt();
		for(int i = 0; i < n; i++) {
			A[i] = scan.nextInt();
		}
		int q = scan.nextInt();
		for(int i = 0; i < q; i++) {
			int m = scan.nextInt();
			if(isSolve(0, m)) {
				System.out.println("yes");
			}else {
				System.out.println("no");
			}
		}
		scan.close();
	}
	public static boolean isSolve(int i, int m) {
		if(m == 0) return true;
		if(i >= n) return false;
		boolean res = isSolve(i + 1, m) || isSolve(i + 1, m - A[i]);
		return res;
	}
}
