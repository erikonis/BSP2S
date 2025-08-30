import java.util.*;

public class Main {
    private static Map<Long, Long> memo;
    private static long A, B, C, D;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        while (T-- > 0) {
            long N = sc.nextLong();
            A = sc.nextLong();
            B = sc.nextLong();
            C = sc.nextLong();
            D = sc.nextLong();
            memo = new HashMap<>();
            System.out.println(solve(N));
        }
        sc.close();
    }

    private static long solve(long n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return D;
        }
        if (memo.containsKey(n)) {
            return memo.get(n);
        }

        long ans = n * D;

        // Multiply by 2
        long cost2 = (n % 2 == 0) ? A : Math.min(A + D, A + D) + D; // (n+1)*D or (n-1)*D to get even, then A. No need for Math.min(A+D, A+D), it's just A+D
        ans = Math.min(ans, solve(n / 2) + cost2 * (n % 2 == 0 ? 1 : (n % 2 == 1 ? 1 : 0))); // Simplified

        // Multiply by 3
        long cost3 = (n % 3 == 0) ? B : Math.min(B + (n % 3) * D, B + (3 - (n % 3)) * D);
        ans = Math.min(ans, solve(n / 3) + cost3);

        // Multiply by 5
        long cost5 = (n % 5 == 0) ? C : Math.min(C + (n % 5) * D, C + (5 - (n % 5)) * D);
        ans = Math.min(ans, solve(n / 5) + cost5);
        
        // Direct approach (n-1)*D or (n+1)*D
        ans = Math.min(ans, (n + 1) * D + solve((n + 1) / 2) + A);
        ans = Math.min(ans, (n - 1) * D + solve((n - 1) / 2) + A);
        ans = Math.min(ans, (n + 1) * D + solve((n + 1) / 3) + B);
        ans = Math.min(ans, (n - 1) * D + solve((n - 1) / 3) + B);
        ans = Math.min(ans, (n + 2) * D + solve((n + 2) / 3) + B);
        ans = Math.min(ans, (n - 2) * D + solve((n - 2) / 3) + B);
        ans = Math.min(ans, (n + 1) * D + solve((n + 1) / 5) + C);
        ans = Math.min(ans, (n - 1) * D + solve((n - 1) / 5) + C);
        ans = Math.min(ans, (n + 2) * D + solve((n + 2) / 5) + C);
        ans = Math.min(ans, (n - 2) * D + solve((n - 2) / 5) + C);
        ans = Math.min(ans, (n + 3) * D + solve((n + 3) / 5) + C);
        ans = Math.min(ans, (n - 3) * D + solve((n - 3) / 5) + C);
        ans = Math.min(ans, (n + 4) * D + solve((n + 4) / 5) + C);
        ans = Math.min(ans, (n - 4) * D + solve((n - 4) / 5) + C);


        memo.put(n, ans);
        return ans;
    }
}