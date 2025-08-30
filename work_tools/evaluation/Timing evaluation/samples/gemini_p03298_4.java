import java.util.*;

public class Main {
    static int N;
    static String S;
    static long ans = 0;
    static HashMap<String, long[]> map1 = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        S = sc.next();

        // Generate all possible red/blue assignments for the first half of the string
        // The state is represented by (red_string, blue_string_reversed, red_count, blue_count)
        dfs1(0, "", "", 0, 0);

        // Iterate through all possible red/blue assignments for the second half
        // and check for matches with the first half
        dfs2(N, "", "", 0, 0);

        System.out.println(ans);
    }

    // DFS for the first half of the string (indices 0 to N-1)
    private static void dfs1(int idx, String red, String blue, int rCnt, int bCnt) {
        if (idx == N) {
            String key = red + "#" + blue; // Combine red and blue (reversed) strings as a key
            long[] counts = map1.getOrDefault(key, new long[N + 1]);
            counts[rCnt]++; // Increment count for this (red_string, blue_string_reversed) pair and red_count
            map1.put(key, counts);
            return;
        }

        // Color current character red
        dfs1(idx + 1, red + S.charAt(idx), blue, rCnt + 1, bCnt);

        // Color current character blue
        dfs1(idx + 1, red, S.charAt(idx) + blue, rCnt, bCnt + 1);
    }

    // DFS for the second half of the string (indices N to 2N-1)
    private static void dfs2(int idx, String red, String blue, int rCnt, int bCnt) {
        if (idx == 2 * N) {
            String key = red + "#" + blue; // Combine red and blue (reversed) strings as a key
            if (map1.containsKey(key)) {
                long[] counts = map1.get(key);
                for (int i = 0; i <= N; i++) {
                    // Total red count must be equal to total blue count for strings to be equal length
                    if (i + rCnt == N) {
                        ans += counts[i];
                    }
                }
            }
            return;
        }

        // Color current character red
        dfs2(idx + 1, red + S.charAt(idx), blue, rCnt + 1, bCnt);

        // Color current character blue
        dfs2(idx + 1, red, S.charAt(idx) + blue, rCnt, bCnt + 1);
    }
}