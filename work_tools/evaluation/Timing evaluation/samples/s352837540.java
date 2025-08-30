
import java.util.Scanner;

public class Main {

  static final int MOD = 1000000007;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int N = scanner.nextInt();
    int K = scanner.nextInt();
    int[] arr = new int[N];
    for (int i = 0; i < N; i++) {
      arr[i] = scanner.nextInt();
    }
    System.out.println(solve(arr, N, K));
  }

  private static int add_self(int a, int b) {
    a += b;
    if (a >= MOD) {
      a -= MOD;
    }
    return a;
  }

  private static int sub_self(int a, int b) {
    a -= b;
    if (a < 0) {
      a += MOD;
    }
    return a;
  }

  private static long solve(int[] arr, int n, int k) {

    int[] dp = new int[k + 1];
    dp[0] = 1; // the number of ways such that we used i candies so far
    for (int child = 0; child < n; ++child) {
      int up_to = arr[child];
      int[] fake = new int[k + 1];
      for (int used = k; used >= 0; --used) {
        int tmp = dp[used];
        int L = used + 1;
        int R = used + Math.min(up_to, k - used);
        if (L <= R) {
          fake[L] = add_self(fake[L], tmp);
          if (R + 1 <= k) {
            fake[R + 1] = sub_self(fake[R + 1], tmp);
          }
        }
      }
      int prefix_sum = 0;
      for (int i = 0; i <= k; ++i) {
        prefix_sum = add_self(prefix_sum, fake[i]);
        dp[i] = add_self(dp[i], prefix_sum);
      }
    }
    return dp[k];
  }
}
