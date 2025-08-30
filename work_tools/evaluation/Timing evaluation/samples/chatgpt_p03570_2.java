import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        int n = s.length();

        int res = 0;
        int mask = 0;

        // Use a map to record the earliest index for each parity mask
        // We use an int array of size 1<<26 (2^26) for efficiency.
        // But here, we'll just use a HashMap for clarity and lower memory usage.
        java.util.HashMap<Integer, Integer> map = new java.util.HashMap<>();
        map.put(0, -1);

        int[] dp = new int[n + 1];
        java.util.Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for (int i = 0; i < n; i++) {
            mask ^= 1 << (s.charAt(i) - 'a');
            // Default: add one more chunk
            dp[i + 1] = Math.min(dp[i + 1], dp[i] + 1);

            // Check for same mask previously seen
            if (map.containsKey(mask)) {
                int idx = map.get(mask);
                dp[i + 1] = Math.min(dp[i + 1], dp[idx + 1]);
            }

            // Check for masks differing by one bit (allow one odd character)
            for (int c = 0; c < 26; c++) {
                int mask2 = mask ^ (1 << c);
                if (map.containsKey(mask2)) {
                    int idx = map.get(mask2);
                    dp[i + 1] = Math.min(dp[i + 1], dp[idx + 1] + 1);
                }
            }

            // Only record first occurrence to avoid overwriting with later (worse) indices
            if (!map.containsKey(mask)) map.put(mask, i);
        }
        System.out.println(dp[n]);
    }
}