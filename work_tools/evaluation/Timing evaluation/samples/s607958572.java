import java.util.*;

public class Main {
	static Scanner sc = new Scanner(System.in);
	static int dp[];
	static int N, M;
	static int[] matchsticks = {
		2,
		5,
		5,
		4,
		5,
		6,
		3,
		7,
		6
	};
	static ArrayList<String> possibleAns = new ArrayList<String>();
	static Digit[] digits;

	public static void main(String[] args) {
		N = sc.nextInt();
		M = sc.nextInt();
		digits  = new Digit[M];
		dp = new int[N+1];
		Arrays.fill(dp, -1);
		for(int i = 0; i < M; i++) {
			int curr = sc.nextInt();
			digits[i] = new Digit(curr, matchsticks[curr-1]);
		}

		Arrays.sort(digits);

		// for(Digit d : digits) {
		// 	System.out.println(d.digit + " " + d.matchsticks);
		// }

		int length = dp(N);
		String ans = formDigits(length);

		System.out.println(ans);
	}

	public static String formDigits(int length) {
		StringBuilder sb = new StringBuilder();
		int index = M-1;
		int copyN = N;
		while(length > 0 && index >= 0) {
			if(dp(copyN - digits[index].matchsticks) == dp(copyN)-1) {
				sb.append(digits[index].digit);
				length--;
				copyN -= digits[index].matchsticks;
			}
			else {
				index--;
			}
		}

		return sb.toString();
	}

	public static int dp(int i) {
		if(i == 0) return 0;

		if(i < 0) return Integer.MIN_VALUE;

		if(dp[i] == -1) {
			int ans = Integer.MIN_VALUE;
			for(int j = 0; j < M; j++) {
				ans = Math.max(ans, dp(i-digits[j].matchsticks) + 1);
			}

			dp[i] = ans;
		}

		return dp[i];
	}

	static class Digit implements Comparable<Digit> {
		public int digit, matchsticks;

		public Digit(int d, int m) {
			digit = d;
			matchsticks = m;
		}

		public int compareTo(Digit b) {
			return new Integer(this.digit).compareTo(b.digit);
		}
	}
}