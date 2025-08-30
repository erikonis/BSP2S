import java.util.*;

public class Main {
    static final int MOD = 1000000007;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        String S1 = sc.next();
        String S2 = sc.next();

        long res = 1;
        int i = 0;
        boolean prevVertical = false;

        while (i < N) {
            if (S1.charAt(i) == S2.charAt(i)) {
                // Vertical domino
                if (i == 0) {
                    res = (res * 3) % MOD;
                } else {
                    if (prevVertical) {
                        res = (res * 2) % MOD;
                    } else {
                        res = (res * 1) % MOD;
                    }
                }
                prevVertical = true;
                i++;
            } else {
                // Horizontal domino
                if (i == 0) {
                    res = (res * 6) % MOD;
                } else {
                    if (prevVertical) {
                        res = (res * 2) % MOD;
                    } else {
                        res = (res * 3) % MOD;
                    }
                }
                prevVertical = false;
                i += 2;
            }
        }
        System.out.println(res);
    }
}