import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String A = scanner.next();
        int n = A.length();
        Set<String> uniqueStrings = new HashSet<>();
        uniqueStrings.add(A); // The original string is always possible

        // Precompute prefix and suffix arrays to store first and last differing positions
        int[] firstDiff = new int[n];
        int[] lastDiff = new int[n];
        
        firstDiff[0] = -1;
        for (int i = 1; i < n; i++) {
            if (A.charAt(i) != A.charAt(i-1)) {
                firstDiff[i] = i-1;
            } else {
                firstDiff[i] = firstDiff[i-1];
            }
        }
        
        lastDiff[n-1] = -1;
        for (int i = n-2; i >= 0; i--) {
            if (A.charAt(i) != A.charAt(i+1)) {
                lastDiff[i] = i+1;
            } else {
                lastDiff[i] = lastDiff[i+1];
            }
        }
        
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (i == 0 && j == n-1) continue; // already counted as original string
                // Check if reversing i..j actually changes the string
                boolean canChange = false;
                if (i > 0 && j < n-1) {
                    if (firstDiff[j+1] >= i || lastDiff[i-1] <= j) {
                        canChange = true;
                    }
                } else if (i > 0) {
                    if (lastDiff[i-1] != -1) {
                        canChange = true;
                    }
                } else if (j < n-1) {
                    if (firstDiff[j+1] != -1) {
                        canChange = true;
                    }
                }
                
                if (canChange) {
                    StringBuilder sb = new StringBuilder(A);
                    String reversed = new StringBuilder(A.substring(i, j+1)).reverse().toString();
                    sb.replace(i, j+1, reversed);
                    uniqueStrings.add(sb.toString());
                }
            }
        }
        System.out.println(uniqueStrings.size());
    }
}