import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String S = sc.next();
        String base = "AKIHABARA";

        // Positions in base where 'A' is optional (can be removed)
        int[] optionalA = {0, 4, 6, 8};

        // There are 2^4 = 16 possible ways to remove A's from these positions
        for (int mask = 0; mask < 16; mask++) {
            StringBuilder sb = new StringBuilder(base);
            // Remove from higher indices first to keep indices valid
            for (int i = 3; i >= 0; i--) {
                if (((mask >> i) & 1) == 1) {
                    sb.deleteCharAt(optionalA[i]);
                }
            }
            if (sb.toString().equals(S)) {
                System.out.println("YES");
                return;
            }
        }
        System.out.println("NO");
    }
}