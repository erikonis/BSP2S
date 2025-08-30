import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        sc.close();

        Arrays.sort(a);

        long maxVal = -1;
        int bestAi = -1;
        int bestAj = -1;

        // The largest element in the sorted array will be our candidate for a_i
        int ai = a[n - 1];

        // We want to find a_j such that comb(ai, aj) is maximized.
        // The function comb(N, K) increases as K approaches N/2.
        // So, we need to find the element a_j in the array that is closest to ai / 2.
        int target = ai / 2;

        int aj = -1;
        int minDiff = Integer.MAX_VALUE;

        // Iterate through all elements except the largest one (which is ai)
        for (int i = 0; i < n - 1; i++) {
            if (Math.abs(a[i] - target) < minDiff) {
                minDiff = Math.abs(a[i] - target);
                aj = a[i];
            }
        }
        
        System.out.println(ai + " " + aj);
    }
}