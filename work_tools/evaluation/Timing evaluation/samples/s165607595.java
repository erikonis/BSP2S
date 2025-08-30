import java.util.Scanner;

public class Main {

	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {

			while (sc.hasNext()) {
				int n = Integer.parseInt(sc.next());
					if (n == 0) {
						break;
					} else {
						judge(n);
					}
			}
		sc.close();
	}

	private static void judge(int n) {
		int a = 0;
		int b = 0;
		int A = 0;
		int B = 0;

			for (int i = 0; i < n; i++) {
				a = Integer.parseInt(sc.next());
				b = Integer.parseInt(sc.next());

				if (a > b) {
					A += a + b;
				} else if (a < b) {
					B += a + b;
				} else {
					A += a;
					B += b;
				}
			}

		System.out.printf("%d %d\n", A, B);
	}

}
