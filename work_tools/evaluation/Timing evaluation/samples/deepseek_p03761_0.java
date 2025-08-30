import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        String[] strings = new String[n];
        for (int i = 0; i < n; i++) {
            strings[i] = scanner.next();
        }

        // Initialize minFreq with maximum possible values
        int[] minFreq = new int[26];
        Arrays.fill(minFreq, Integer.MAX_VALUE);

        for (String s : strings) {
            int[] freq = new int[26];
            for (char c : s.toCharArray()) {
                freq[c - 'a']++;
            }
            for (int i = 0; i < 26; i++) {
                minFreq[i] = Math.min(minFreq[i], freq[i]);
            }
        }

        StringBuilder result = new StringBuilder();
        for (char c = 'a'; c <= 'z'; c++) {
            int count = minFreq[c - 'a'];
            for (int i = 0; i < count; i++) {
                result.append(c);
            }
        }

        System.out.println(result.toString());
    }
}