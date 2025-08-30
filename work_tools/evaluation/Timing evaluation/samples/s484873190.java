import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();
		char[][]A = new char[N][N];
		char[][]B = new char[M][M];
		for(int i = 0; i < N; i++) {
			String t = sc.next();
			for(int j = 0; j < N; j++) {
				A[i][j] = t.charAt(j);
			}
		}
		for(int i = 0; i < M; i++) {
			String t = sc.next();
			for(int j = 0; j < M; j++) {
				B[i][j] = t.charAt(j);
			}
		}
		int k = N - M;
		for(int i = 0; i <= k; i++) {
			for(int j = 0; j <= k; j++) {
				boolean flag = true;
				loop: for(int r = 0; r < M; r++) {
					for(int c = 0; c < M; c++) {
						if(A[r + i][c + j] != B[r][c]) {
							flag = false;
							break loop;
						}
					}
				}
				if(flag) {
					System.out.println("Yes");
					System.exit(0);
				}
			}
		}
		System.out.println("No");
	}
}
