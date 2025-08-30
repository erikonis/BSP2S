import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int height = sc.nextInt();
		int width = sc.nextInt();
		int num = sc.nextInt();

		long dist[][] = new long[height][width];
		dist[0][0] = 0;
		for(int i = 1; i < height; i ++) {
			dist[i][0] = dist[i - 1][0] + i;
		}
		for(int i = 1; i < width; i ++) {
			dist[0][i] = dist[0][i - 1] + i;
		}
		for(int i = 1; i < height; i ++) {
			for(int j = 1; j < width; j ++) {
				dist[i][j] = dist[i - 1][j] + dist[i][j - 1] - dist[i - 1][j - 1] + i + j;
				dist[i][j] = mod(dist[i][j]);
			}
		}
		long sum = 0;
		long sumHeight = 0;
		long sumWidth = 0;
		for(int i = 0; i < height; i ++) {
			for(int j = 0; j < width; j ++) {
				sum += dist[i][j];
				sum = mod(sum);
			}
		}
		for(int i = 0; i < height; i ++) {
			sumHeight += dist[i][0];
			sumHeight = mod(sumHeight);
		}
		sumHeight = mod(sumHeight * width);
		for(int i = 0; i < width; i ++) {
			sumWidth += dist[0][i];
			sumWidth = mod(sumWidth);
		}
		sumWidth = mod(sumWidth * height);


		long ans = mod(sum * 2 - sumHeight - sumWidth);
		ans = mod(ans * combiMod(width * height - 2, num - 2));
		System.out.println(ans);
	}

	static long MOD = (long)Math.pow(10, 9) + 7;
	public static long mod(long i) {
		return i % MOD + ((i % MOD) < 0 ? MOD : 0);
	}

	static long powerMod(long x, long y) {
		if (y == 0) {
			return 1;
		} else if (y == 1) {
			return x;
		} else if (y % 2 == 0) {
			long tmp = powerMod(x, y / 2);
			return mod(tmp * tmp);
		} else {
			long tmp = powerMod(x, y / 2);
			return mod(mod(tmp * tmp) * x);
		}
	}
	
	static long divMod(long x, long y) {
		return mod(x * powerMod(y, MOD - 2));
	}

	static long combiMod(int n, int r) {
		if(n <= 0 || n < r) { return 0; }
		r = Math.min(n - r, r);
		if(r == 0) { return 1; }
		long ans = 1;
		for (int i = 1; i <= r; i ++) {
			ans = mod(ans * (n - i + 1));
			ans = divMod(ans, i);
		}
		return mod(ans);
	}
}