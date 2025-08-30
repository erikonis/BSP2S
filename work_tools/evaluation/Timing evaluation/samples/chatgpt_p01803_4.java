import java.util.*;

public class Main {
    static boolean isVowel(char c) {
        return c == 'a' || c == 'i' || c == 'u' || c == 'e' || c == 'o';
    }

    static String extract(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            if (isVowel(s.charAt(i - 1))) {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int n = Integer.parseInt(sc.nextLine());
            if (n == 0) break;
            String[] names = new String[n];
            int maxLen = 0;
            for (int i = 0; i < n; i++) {
                names[i] = sc.nextLine();
                maxLen = Math.max(maxLen, extract(names[i]).length());
            }
            int answer = -1;
            for (int k = 1; k <= maxLen; k++) {
                Set<String> set = new HashSet<>();
                boolean unique = true;
                for (int i = 0; i < n; i++) {
                    String code = extract(names[i]);
                    if (code.length() > k)
                        code = code.substring(0, k);
                    set.add(code);
                }
                if (set.size() == n) {
                    answer = k;
                    break;
                }
            }
            System.out.println(answer);
        }
    }
}