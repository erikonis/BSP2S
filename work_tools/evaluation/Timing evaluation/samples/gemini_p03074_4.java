import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();
        String S = sc.next();
        sc.close();

        // Compress the string into blocks of consecutive identical characters
        // Example: "00010" -> [ (0, 3), (1, 1), (0, 1) ]
        // Example: "11101010110011" -> [ (1, 3), (0, 1), (1, 1), (0, 1), (1, 1), (0, 1), (1, 2), (0, 2), (1, 2) ]
        List<Integer> blocks = new ArrayList<>();
        if (N > 0) {
            char currentChar = S.charAt(0);
            int count = 0;
            for (int i = 0; i < N; i++) {
                if (S.charAt(i) == currentChar) {
                    count++;
                } else {
                    blocks.add(count);
                    currentChar = S.charAt(i);
                    count = 1;
                }
            }
            blocks.add(count); // Add the last block
        }

        // If the first block is '0', add a dummy block of '0' at the beginning
        // This simplifies handling indices for sliding window
        // [0, 3], [1, 1], [0, 1] becomes [0,0], [0,3], [1,1], [0,1]
        // This ensures all '1' blocks start at an odd index in 'blocks' list
        if (S.charAt(0) == '0') {
            blocks.add(0, 0);
        }

        int maxOnes = 0;
        int currentOnes = 0;
        int left = 0; // Left pointer for the sliding window of blocks

        // The number of '0' blocks we can flip (at most K)
        // Each '0' block corresponds to an even index in 'blocks' (if we start with dummy 0)
        // Or odd index if first block is 1
        int numZeroBlocksFlipped = 0;

        // Iterate through the blocks, with 'right' as the right pointer of the sliding window
        for (int right = 0; right < blocks.size(); right++) {
            // Add the current block to the window
            currentOnes += blocks.get(right);

            // If the current block represents '0's (even index in blocks list, or odd if no dummy 0 and first char is 1)
            // and it's not the dummy '0' block
            if (right % 2 == 1) { // This block is '0's (after the dummy 0, 1st actual block is 0s, 2nd is 1s, etc.)
                numZeroBlocksFlipped++;
            }

            // Shrink the window if we have flipped more than K '0' blocks
            while (numZeroBlocksFlipped > K) {
                currentOnes -= blocks.get(left);
                if (left % 2 == 1) { // This block was '0's
                    numZeroBlocksFlipped--;
                }
                left++;
            }

            maxOnes = Math.max(maxOnes, currentOnes);
        }

        // Handle the case where the last block is '0' and we might want to flip it.
        // The problem is phrased such that we want consecutive hands.
        // If the last block is '0', we can extend the '1's to include it.
        // The current logic already handles this by adding the '0' block to `currentOnes` and
        // incrementing `numZeroBlocksFlipped`. The `while` loop will then correctly shrink.

        // One edge case: if all characters are '1's, or after flipping all '0's,
        // the total length is N.
        maxOnes = Math.min(maxOnes, N); // The max number of consecutive people cannot exceed N.

        System.out.println(maxOnes);
    }
}