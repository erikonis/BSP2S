import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner stdIn = new Scanner(System.in);
		while(true) {
			int N = stdIn.nextInt();
			int M = stdIn.nextInt();
			if(N == 0 && M == 0) {
				break;
			}
			int[] P = new int[N+1];
			for(int i = 0; i < N; i++) {
				P[i] = stdIn.nextInt();
			}
			P[N] = 0;
			int[] k = new int[(N+1)*(N+1)];
			//--- N^2 ---//
			for(int i = 0; i < N+1; i++) {
				for(int j = 0; j < N+1; j++) {
					k[i * N + j] = P[i] + P[j];
				}
			}
			
			Arrays.sort(k);
			int S = 0;
			IN:for(int j = 0; j < M; j++) {
				for(int i = 0; i < (N+1)*(N+1); i++) {
					if(Arrays.binarySearch(k, M-j-k[i]) >= 0) {
						System.out.println(M-j);
						break IN;
					}
				}
			}
		}
	}
}