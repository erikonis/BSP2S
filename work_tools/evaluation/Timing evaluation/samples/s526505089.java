import java.util.Scanner;

public class Main {
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		while (true) {
			int X = sc.nextInt();
			if (X == 0) break;
			int Y = sc.nextInt();
			int S = sc.nextInt();
			int ans = 0;
			for (int i = 1; i < S; ++i) {
				int pa = i * (100 + X) / 100;
				for (int j = 1; j < S; ++j) {
					int pb = j * (100 + X) / 100;
					if (pa + pb != S) continue;
					int aa = i * (100 + Y) / 100;
					int ab = j * (100 + Y) / 100;
					ans = Math.max(ans, aa + ab);
				}
			}
			System.out.println(ans);
		}
	}

}