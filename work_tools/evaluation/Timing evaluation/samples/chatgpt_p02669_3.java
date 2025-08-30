import java.util.*;
import java.io.*;

public class Main {
    static HashMap<Long, Long> memo;
    static long A, B, C, D;
    
    static long dfs(long n) {
        if (n == 0) return 0;
        if (n == 1) return D;
        if (memo.containsKey(n)) return memo.get(n);
        long res = n * D;
        // Try all multiplications
        // For division by 2
        if (n % 2 == 0) {
            res = Math.min(res, dfs(n / 2) + A);
        } else {
            // Increase to divisible by 2
            res = Math.min(res, dfs(n / 2) + A + (n - n / 2 * 2) * D);
            // Decrease to divisible by 2
            res = Math.min(res, dfs(n / 2 + 1) + A + ((n / 2 + 1) * 2 - n) * D);
        }
        // For division by 3
        if (n % 3 == 0) {
            res = Math.min(res, dfs(n / 3) + B);
        } else {
            // Increase to divisible by 3
            res = Math.min(res, dfs(n / 3) + B + (n - n / 3 * 3) * D);
            // Decrease to divisible by 3
            res = Math.min(res, dfs(n / 3 + 1) + B + ((n / 3 + 1) * 3 - n) * D);
        }
        // For division by 5
        if (n % 5 == 0) {
            res = Math.min(res, dfs(n / 5) + C);
        } else {
            // Increase to divisible by 5
            res = Math.min(res, dfs(n / 5) + C + (n - n / 5 * 5) * D);
            // Decrease to divisible by 5
            res = Math.min(res, dfs(n / 5 + 1) + C + ((n / 5 + 1) * 5 - n) * D);
        }
        memo.put(n, res);
        return res;
    }
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        while (T-- > 0) {
            String[] tokens = br.readLine().split(" ");
            long N = Long.parseLong(tokens[0]);
            A = Long.parseLong(tokens[1]);
            B = Long.parseLong(tokens[2]);
            C = Long.parseLong(tokens[3]);
            D = Long.parseLong(tokens[4]);
            memo = new HashMap<>();
            long ans = dfs(N);
            System.out.println(ans);
        }
    }
}