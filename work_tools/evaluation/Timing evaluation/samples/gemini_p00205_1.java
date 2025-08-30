import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int[] hands = new int[5];
            boolean endOfInput = true;
            for (int i = 0; i < 5; i++) {
                hands[i] = sc.nextInt();
                if (hands[i] != 0) {
                    endOfInput = false;
                }
            }

            if (endOfInput) {
                break;
            }

            int[] results = new int[5];

            // Count occurrences of each hand
            int[] handCounts = new int[4]; // index 1 for Goo, 2 for Choki, 3 for Par
            for (int hand : hands) {
                handCounts[hand]++;
            }

            // Check for Aiko (all same or all three hands present)
            if (handCounts[1] == 5 || handCounts[2] == 5 || handCounts[3] == 5 ||
                (handCounts[1] > 0 && handCounts[2] > 0 && handCounts[3] > 0)) {
                for (int i = 0; i < 5; i++) {
                    results[i] = 3; // Aiko
                }
            } else {
                // Determine winners and losers
                int winningHand = 0;
                int losingHand = 0;

                if (handCounts[1] > 0 && handCounts[2] > 0) { // Goo vs Choki
                    winningHand = 1; // Goo wins
                    losingHand = 2;  // Choki loses
                } else if (handCounts[2] > 0 && handCounts[3] > 0) { // Choki vs Par
                    winningHand = 2; // Choki wins
                    losingHand = 3;  // Par loses
                } else if (handCounts[3] > 0 && handCounts[1] > 0) { // Par vs Goo
                    winningHand = 3; // Par wins
                    losingHand = 1;  // Goo loses
                }

                for (int i = 0; i < 5; i++) {
                    if (hands[i] == winningHand) {
                        results[i] = 1; // Win
                    } else if (hands[i] == losingHand) {
                        results[i] = 2; // Lose
                    }
                }
            }

            for (int result : results) {
                System.out.println(result);
            }
        }
        sc.close();
    }
}