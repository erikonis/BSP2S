import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        String S = sc.next();
        Set<Character> chars = new HashSet<>();
        for (char c : S.toCharArray()) {
            chars.add(c);
        }

        if (S.length() < 26) {
            for (char c = 'a'; c <= 'z'; c++) {
                if (!chars.contains(c)) {
                    System.out.println(S + c);
                    return;
                }
            }
        } else {
            for (int i = 25; i >= 0; i--) {
                char current = S.charAt(i);
                for (char c = (char)(current + 1); c <= 'z'; c++) {
                    if (!S.substring(0, i).contains(String.valueOf(c))) {
                        System.out.println(S.substring(0, i) + c);
                        return;
                    }
                }
            }
            System.out.println(-1);
        }
    }
}