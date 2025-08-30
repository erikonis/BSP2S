import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		System.out.println(solve());
	}
	
	public static String solve() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();
		StringBuffer sb = new StringBuffer();
		int max = 0, min = 0;
		if (M > N) {
			max = M;
			min = N;
		}
		else {
			max = N;
			min = M;
		}
			for (int i = 0; i < max; i++) {
				sb.append(min);
			}
		return sb.toString();
	}
}