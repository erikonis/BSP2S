import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int A = sc.nextInt(); // Number of 500-yen coins
        int B = sc.nextInt(); // Number of 100-yen coins
        int C = sc.nextInt(); // Number of 50-yen coins
        int X = sc.nextInt(); // Target sum

        int count = 0;

        // Iterate through all possible counts of 500-yen coins
        for (int i = 0; i <= A; i++) {
            // Calculate the remaining amount needed after using 'i' 500-yen coins
            int remainingX = X - (i * 500);

            // If remainingX is negative, this combination is too large, break early
            if (remainingX < 0) {
                break;
            }

            // Iterate through all possible counts of 100-yen coins
            for (int j = 0; j <= B; j++) {
                // Calculate the remaining amount needed after using 'j' 100-yen coins
                int finalRemainingX = remainingX - (j * 100);

                // If finalRemainingX is negative, this combination is too large, break early
                if (finalRemainingX < 0) {
                    break;
                }

                // Check if the remaining amount can be formed using 50-yen coins
                // and if the number of 50-yen coins needed is within the available limit C
                if (finalRemainingX % 50 == 0 && (finalRemainingX / 50) <= C) {
                    count++;
                }
            }
        }
        System.out.println(count);
        sc.close();
    }
}