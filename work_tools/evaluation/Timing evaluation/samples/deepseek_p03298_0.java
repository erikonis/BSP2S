import java.util.*;
import java.io.*;

public class Main {
    static int N;
    static String S;
    static long res = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        S = br.readLine();

        Map<String, Long>[] map = new HashMap[N+1];
        for (int i = 0; i <= N; i++) {
            map[i] = new HashMap<>();
        }

        // Process first half
        for (int mask = 0; mask < (1 << N); mask++) {
            StringBuilder red = new StringBuilder();
            StringBuilder blue = new StringBuilder();
            for (int i = 0; i < N; i++) {
                if ((mask & (1 << i)) != 0) {
                    red.append(S.charAt(i));
                } else {
                    blue.append(S.charAt(i));
                }
            }
            String key = red.toString() + "|" + new StringBuilder(blue).reverse().toString();
            map[red.length()].put(key, map[red.length()].getOrDefault(key, 0L) + 1);
        }

        // Process second half and combine
        for (int mask = 0; mask < (1 << N); mask++) {
            StringBuilder red = new StringBuilder();
            StringBuilder blue = new StringBuilder();
            for (int i = 0; i < N; i++) {
                if ((mask & (1 << i)) != 0) {
                    red.append(S.charAt(N + i));
                } else {
                    blue.append(S.charAt(N + i));
                }
            }
            String key = new StringBuilder(blue).reverse().toString() + "|" + red.toString();
            int k = N - red.length();
            if (k >= 0 && k <= N) {
                res += map[k].getOrDefault(key, 0L);
            }
        }

        System.out.println(res);
    }
}