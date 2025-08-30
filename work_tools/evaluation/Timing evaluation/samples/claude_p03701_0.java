import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] scores = new int[n];
        
        for (int i = 0; i < n; i++) {
            scores[i] = sc.nextInt();
        }
        
        // Generate all possible sums using bit manipulation
        Set<Integer> possibleSums = new HashSet<>();
        
        // For each subset (represented by bits)
        for (int mask = 0; mask < (1 << n); mask++) {
            int sum = 0;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    sum += scores[i];
                }
            }
            possibleSums.add(sum);
        }
        
        // Find maximum sum that is not a multiple of 10
        int maxDisplayed = 0;
        for (int sum : possibleSums) {
            if (sum % 10 != 0) {
                maxDisplayed = Math.max(maxDisplayed, sum);
            }
        }
        
        System.out.println(maxDisplayed);
        sc.close();
    }
}