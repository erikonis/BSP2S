import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<int[]> diamond = new ArrayList<>();
        ArrayList<Integer> rowLens = new ArrayList<>();
        // Read input
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) break;
            String[] tokens = line.split(",");
            int[] row = new int[tokens.length];
            for (int i = 0; i < tokens.length; i++) row[i] = Integer.parseInt(tokens[i]);
            diamond.add(row);
            rowLens.add(tokens.length);
        }
        int N = (rowLens.size() + 1) / 2; // upper half rows
        int size = diamond.size();
        int[][] dp = new int[size][];
        // Copy diamond to dp
        for (int i = 0; i < size; i++) {
            dp[i] = Arrays.copyOf(diamond.get(i), diamond.get(i).length);
        }
        // Bottom up DP
        for (int i = size - 2; i >= 0; i--) {
            int len = dp[i].length;
            if (i < N - 1) { // upper triangle
                for (int j = 0; j < len; j++) {
                    dp[i][j] += Math.max(dp[i+1][j], dp[i+1][j+1]);
                }
            } else { // lower triangle
                for (int j = 0; j < len; j++) {
                    dp[i][j] += Math.max(dp[i+1][j], dp[i+1][j-1]);
                }
            }
        }
        System.out.println(dp[0][0]);
    }
}