import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        String S = sc.next();

        // left_black[i] stores the count of black stones from index 0 to i-1.
        int[] leftBlack = new int[N + 1];
        // right_white[i] stores the count of white stones from index i to N-1.
        int[] rightWhite = new int[N + 1];

        // Populate leftBlack array
        for (int i = 0; i < N; i++) {
            leftBlack[i + 1] = leftBlack[i];
            if (S.charAt(i) == '#') {
                leftBlack[i + 1]++;
            }
        }

        // Populate rightWhite array
        for (int i = N - 1; i >= 0; i--) {
            rightWhite[i] = rightWhite[i + 1];
            if (S.charAt(i) == '.') {
                rightWhite[i]++;
            }
        }

        int minRecolors = N; // Initialize with maximum possible recolors

        // Iterate through all possible split points 'i'
        // 'i' represents the first index that will be white.
        // Stones from 0 to i-1 will be black.
        // Stones from i to N-1 will be white.
        for (int i = 0; i <= N; i++) {
            // Count of black stones to change to white (from index i to N-1)
            // This is equivalent to rightWhite[i] (white stones in the right part)
            // minus the number of black stones in the right part.
            // Or, simply the number of black stones in the right part.
            // The number of black stones from index i to N-1 is (leftBlack[N] - leftBlack[i]).
            // These need to be changed to white.

            // Count of white stones to change to black (from index 0 to i-1)
            // This is equivalent to leftBlack[i] (black stones in the left part)
            // minus the number of white stones in the left part.
            // Or, simply the number of white stones from index 0 to i-1.
            // The number of white stones from index 0 to i-1 is (i - leftBlack[i]).
            // These need to be changed to black.

            // Total recolors for this split point 'i':
            // (Number of black stones in prefix 0 to i-1) + (Number of white stones in suffix i to N-1)
            // No, this is incorrect.
            // The number of operations is:
            // (Number of original '.' in 0 to i-1 that need to be changed to '#')
            // + (Number of original '#' in i to N-1 that need to be changed to '.')

            // Number of original '.' in 0 to i-1 is (i - leftBlack[i])
            // Number of original '#' in i to N-1 is (leftBlack[N] - leftBlack[i])

            // The cost for a split at 'i' is:
            // (count of '#' in S[0...i-1]) + (count of '.' in S[i...N-1])
            // This represents making S[0...i-1] all black and S[i...N-1] all white.
            // This is the correct interpretation for the "no white stone immediately to the right of a black stone" condition.
            // The final configuration should look like: ###...###...
            // The split point 'i' means all stones from 0 to i-1 are black, and all stones from i to N-1 are white.

            int currentRecolors = leftBlack[i] + rightWhite[i];
            minRecolors = Math.min(minRecolors, currentRecolors);
        }

        System.out.println(minRecolors);

        sc.close();
    }
}