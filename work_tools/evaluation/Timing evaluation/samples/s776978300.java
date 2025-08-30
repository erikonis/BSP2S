import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int[] x = new int[n];
		int[] y = new int[n];
		for(int i = 0; i < n; i++) {
			x[i] = scanner.nextInt();
			y[i] = x[i];
		}
		Arrays.sort(x);
//		int min = x[n-1/2];
		
		for(int r = 0; r < n; r++) {
			if(y[r] <= x[n/2-1]) {
				System.out.println(x[n/2]);
			}else {
				System.out.println(x[n/2-1]);
			}
		}
	}

}