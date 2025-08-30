import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int n = sc.nextInt();
            if (n == 0) {
                break;
            }

            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = sc.nextInt();
            }

            int remainingMarbles = 32;
            int jiroTurnCount = 0;

            while (remainingMarbles > 0) {
                // Ichiro's turn
                // Ichiro takes (n-1)%5 marbles
                int ichiroTakes = (remainingMarbles - 1) % 5;
                if (ichiroTakes == 0) { // If (n-1)%5 is 0, it means n is 1, 6, 11, ...
                                        // Ichiro wants to leave a multiple of 5 for Jiro.
                                        // If remaining is 1, Ichiro takes 1.
                                        // If remaining is 5, Ichiro takes 4 to leave 1.
                                        // If remaining is 6, Ichiro takes 1 to leave 5.
                    if (remainingMarbles == 1) {
                        ichiroTakes = 1;
                    } else {
                        ichiroTakes = 5 - (remainingMarbles % 5);
                        if (ichiroTakes == 5) ichiroTakes = 0; // Should not happen with (n-1)%5 logic if n%5 != 1
                        if (ichiroTakes == 0) ichiroTakes = 4; // To avoid taking 0, take 4 if remaining is 5
                    }
                }

                if (ichiroTakes > remainingMarbles) {
                    ichiroTakes = remainingMarbles;
                }
                remainingMarbles -= ichiroTakes;
                System.out.println(remainingMarbles);
                if (remainingMarbles == 0) {
                    break;
                }

                // Jiro's turn
                int jiroTakes = a[jiroTurnCount % n];
                if (jiroTakes > remainingMarbles) {
                    jiroTakes = remainingMarbles;
                }
                remainingMarbles -= jiroTakes;
                System.out.println(remainingMarbles);
                jiroTurnCount++;
            }
        }
        sc.close();
    }
}