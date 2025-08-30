import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int L = sc.nextInt();
        int[] a = new int[N];
        for (int i = 0; i < N; i++) a[i] = sc.nextInt();

        // Find a knot where a[i] + a[i+1] >= L
        int idx = -1;
        for (int i = 0; i < N - 1; i++) {
            if (a[i] + a[i + 1] >= L) {
                idx = i;
                break;
            }
        }

        if (idx == -1) {
            System.out.println("Impossible");
            return;
        }

        // Output order: untie all knots except idx+1 in any order, then untie idx+1
        System.out.println("Possible");
        // First, untie from 1 to idx (left side)
        for (int i = 1; i <= idx; i++) {
            System.out.println(i);
        }
        // Then, untie from N-1 down to idx+2 (right side)
        for (int i = N - 1; i > idx + 1; i--) {
            System.out.println(i);
        }
        // Finally, untie the (idx+1)-th knot
        System.out.println(idx + 1);
    }
}