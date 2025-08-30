import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] a = new int[N];
        int maxA = 0;
        for (int i = 0; i < N; i++) {
            a[i] = sc.nextInt();
            if (a[i] > maxA) maxA = a[i];
        }

        // Candidates: m = ai - 1 for all i
        int maxF = 0;
        Set<Integer> candidates = new HashSet<>();
        for (int i = 0; i < N; i++) {
            if (a[i] > 1) candidates.add(a[i] - 1);
        }
        // Add m = LCM of all pairs minus 1 as possible candidates
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                int lcm = (int)((long)a[i] * a[j] / gcd(a[i], a[j]));
                if (lcm > 0 && lcm <= 100_000) candidates.add(lcm - 1);
            }
        }
        // Try all candidates
        for (int m : candidates) {
            int f = 0;
            for (int i = 0; i < N; i++) {
                f += m % a[i];
            }
            if (f > maxF) maxF = f;
        }
        System.out.println(maxF);
    }

    static int gcd(int a, int b) {
        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        return a;
    }
}