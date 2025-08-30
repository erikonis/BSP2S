import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int N = sc.nextInt();
            int T = sc.nextInt();
            int L = sc.nextInt();
            int B = sc.nextInt();
            if (N == 0 && T == 0 && L == 0 && B == 0) {
                break;
            }

            boolean[] loseTurn = new boolean[N + 1];
            boolean[] goBack = new boolean[N + 1];

            for (int i = 0; i < L; i++) {
                loseTurn[sc.nextInt()] = true;
            }
            for (int i = 0; i < B; i++) {
                goBack[sc.nextInt()] = true;
            }

            // dp[t][pos] = probability of being at 'pos' after 't' turns
            double[][] dp = new double[T + 1][N + 1];
            dp[0][0] = 1.0; // Start at position 0 with probability 1

            for (int t = 0; t < T; t++) {
                for (int pos = 0; pos < N; pos++) { // Only consider positions before N
                    if (dp[t][pos] == 0) continue;

                    // If 'lose turn' square, effectively skip this turn for this position
                    if (loseTurn[pos]) {
                        dp[t + 1][pos] += dp[t][pos];
                        continue;
                    }

                    // For each possible dice roll
                    for (int roll = 1; roll <= 6; roll++) {
                        int nextPos = pos + roll;

                        if (nextPos > N) {
                            nextPos = N - (nextPos - N); // Retreat from N
                        }

                        // Handle special squares
                        if (goBack[nextPos]) {
                            dp[t + 1][0] += dp[t][pos] / 6.0; // Go back to start
                        } else if (loseTurn[nextPos]) {
                            // If next position is 'lose turn', this position is effectively "stuck" for one more turn
                            // The probability of reaching nextPos is added to dp[t+2][nextPos]
                            // However, the DP state is defined as `dp[t][pos] = probability of being at 'pos' after 't' turns`
                            // So, if we land on a lose square at turn t+1, we effectively skip turn t+1 movement.
                            // This means we are still at nextPos for turn t+2.
                            // This can be handled by adding the probability to dp[t+2][nextPos].
                            // But since the problem asks for success within T turns, and we need to account for the actual turn count,
                            // a simpler way is to have a state representing "at position X, next turn is skipped".
                            // To keep it simple with [t][pos], if loseTurn[nextPos] is true, then for the next turn,
                            // the checker stays at nextPos. So, effectively, the probability from dp[t][pos]/6.0
                            // contributes to dp[t+2][nextPos]. This implies we need a state for "current position, and whether the next turn is skipped".
                            // This makes the DP state more complex.

                            // A simpler interpretation for loseTurn: if you land on a lose square, you cannot move in the *next* turn.
                            // This means for t+1, the checker is at nextPos. For t+2, it will still be at nextPos.
                            // This implies that the probability from dp[t][pos] / 6.0 should be added to dp[t+1][nextPos].
                            // Then, in the loop for t+1, when processing dp[t+1][nextPos], it will be treated as loseTurn,
                            // and its probability will be forwarded to dp[t+2][nextPos].
                            dp[t + 1][nextPos] += dp[t][pos] / 6.0;
                        } else {
                            dp[t + 1][nextPos] += dp[t][pos] / 6.0;
                        }
                    }
                }
            }

            double totalProb = 0.0;
            for (int t = 1; t <= T; t++) { // Sum up probabilities of reaching N at any turn from 1 to T
                totalProb += dp[t][N];
            }

            System.out.printf("%.6f\n", totalProb);
        }
        sc.close();
    }
}