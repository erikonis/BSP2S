import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String S = sc.next();
        String T = sc.next();
        int minChanges = Integer.MAX_VALUE;
        int n = S.length(), m = T.length();
        for (int i = 0; i <= n - m; i++) {
            int changes = 0;
            for (int j = 0; j < m; j++) {
                if (S.charAt(i + j) != T.charAt(j)) {
                    changes++;
                }
            }
            if (changes < minChanges) {
                minChanges = changes;
            }
        }
        System.out.println(minChanges);
    }
}