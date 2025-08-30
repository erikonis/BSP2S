import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int N = sc.nextInt();
            int M = sc.nextInt();
            if (N == 0 && M == 0) {
                break;
            }

            int[] board = new int[N];
            for (int i = 0; i < N; i++) {
                board[i] = sc.nextInt();
            }

            int currentPosition = 0; // 0-indexed, so 0 represents 1st square
            int rolls = 0;

            for (int i = 0; i < M; i++) {
                int diceRoll = sc.nextInt();
                rolls++;

                currentPosition += diceRoll;

                if (currentPosition >= N - 1) { // N-1 is the last square (Nth square)
                    System.out.println(rolls);
                    // Consume remaining dice rolls for this dataset
                    for (int j = i + 1; j < M; j++) {
                        sc.nextInt();
                    }
                    break;
                }

                currentPosition += board[currentPosition];

                if (currentPosition >= N - 1) {
                    System.out.println(rolls);
                    // Consume remaining dice rolls for this dataset
                    for (int j = i + 1; j < M; j++) {
                        sc.nextInt();
                    }
                    break;
                }
            }
        }
        sc.close();
    }
}