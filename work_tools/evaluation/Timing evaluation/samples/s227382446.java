import java.util.Scanner;

public class Main {
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		while (true) {
			int A = sc.nextInt();
			if (A == 0) break;
			int B = sc.nextInt();
			int D = sc.nextInt();
			int ax = 100000;
			int ay = 100000;
			for (int x = 0; x < ax + ay; ++x) {
				if (x * A >= D) {
					if ((x * A - D) % B == 0) {
						int y = (x * A - D) / B;
						if (x + y < ax + ay || x + y == ax + ay && x * A + y * B < ax * A + ay * B) {
							ax = x;
							ay = y;
						}
					}
				} else {
					if ((D - x * A) % B == 0) {
						int y = (D - x * A) / B;
						if (x + y < ax + ay || x + y == ax + ay && x * A + y * B < ax * A + ay * B) {
							ax = x;
							ay = y;
						}
					}
				}
				if ((x * A + D) % B == 0) {
					int y = (x * A + D) / B;
					if (x + y < ax + ay || x + y == ax + ay && x * A + y * B < ax * A + ay * B) {
						ax = x;
						ay = y;
					}
				}
			}
			System.out.println(ax + " " + ay);
		}
	}
}