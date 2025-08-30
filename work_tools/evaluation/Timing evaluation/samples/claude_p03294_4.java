import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        
        long maxF = 0;
        
        // Check candidates: for each a[i], check multiples minus 1
        Set<Long> candidates = new HashSet<>();
        
        // Add 0 as a candidate
        candidates.add(0L);
        
        // For each a[i], add k*a[i] - 1 for reasonable values of k
        for (int i = 0; i < n; i++) {
            for (int k = 1; k <= 200000 / a[i] + 1; k++) {
                long candidate = (long)k * a[i] - 1;
                if (candidate >= 0) {
                    candidates.add(candidate);
                }
            }
        }
        
        // Check each candidate
        for (long m : candidates) {
            long f = 0;
            for (int i = 0; i < n; i++) {
                f += m % a[i];
            }
            maxF = Math.max(maxF, f);
        }
        
        System.out.println(maxF);
    }
}