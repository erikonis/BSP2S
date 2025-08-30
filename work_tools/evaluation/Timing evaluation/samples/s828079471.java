import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);

		int m = s.nextInt();
		for (int x = 0; x < m; x++) {
			int moneyb = s.nextInt();
			int year = s.nextInt();
			int n = s.nextInt();

			int[] moneya = new int[n];
			int max = 0;

			for (int y = 0; y < n; y++) {
				int rate = s.nextInt();
				double rate_per_year = s.nextDouble();
				int fee = s.nextInt();

				if (rate == 0) {
					moneya[y] = SimpleInterester(moneyb, year, rate_per_year,
							fee);
				} else {
					moneya[y] = CompoundInterester(moneyb, year, rate_per_year,
							fee);
				}
			}

			for (int k = 0; k < n; k++) {
				if (max < moneya[k])
					max = moneya[k];
			}
			System.out.println(max);
		}
		s.close();
	}

	public static int CompoundInterester(int moneyc, int y, double rpy, int fee) {
		for (int i = 0; i < y; i++) {
			int b = (int) (moneyc * rpy);
			moneyc = moneyc + b - fee;
		}
		return moneyc;
	}

	public static int SimpleInterester(int moneys, int y, double rpy, int fee) {
		int b = 0;
		for (int i = 0; i < y; i++) {
			b += (int) (moneys * rpy);
			moneys = moneys - fee;
		}
		moneys += b;
		return moneys;
	}

}