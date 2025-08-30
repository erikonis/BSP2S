import java.util.Scanner;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] s = new int[N];
        int totalSum = 0;
        for (int i = 0; i < N; i++) {
            s[i] = sc.nextInt();
            totalSum += s[i];
        }

        // If the total sum is not a multiple of 10, it will be displayed correctly.
        if (totalSum % 10 != 0) {
            System.out.println(totalSum);
        } else {
            // If the total sum is a multiple of 10, we need to find a way to make it not a multiple of 10.
            // We can do this by removing one question's score that is not a multiple of 10.
            // To maximize the displayed score, we should remove the smallest possible score that is not a multiple of 10.
            Arrays.sort(s); // Sort the scores in ascending order.
            int maxDisplayedScore = 0;
            boolean foundNonMultipleOf10 = false;
            for (int i = 0; i < N; i++) {
                if (s[i] % 10 != 0) {
                    maxDisplayedScore = totalSum - s[i];
                    foundNonMultipleOf10 = true;
                    break; // Found the smallest score not a multiple of 10, remove it.
                }
            }

            if (foundNonMultipleOf10) {
                System.out.println(maxDisplayedScore);
            } else {
                // All scores are multiples of 10. Any sum will be a multiple of 10.
                // In this case, the displayed grade will always be 0.
                System.out.println(0);
            }
        }
        sc.close();
    }
}