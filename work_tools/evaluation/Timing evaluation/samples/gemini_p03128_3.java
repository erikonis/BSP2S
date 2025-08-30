import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int[] A = new int[M];
        for (int i = 0; i < M; i++) {
            A[i] = sc.nextInt();
        }
        sc.close();

        int[] cost = {0, 2, 5, 5, 4, 5, 6, 3, 7, 6}; // cost[i] is matchsticks for digit i

        // Sort available digits by cost (ascending) and then by digit (descending)
        // This ensures we prioritize digits with fewer matchsticks, and among those, larger digits.
        List<int[]> availableDigits = new ArrayList<>();
        for (int digit : A) {
            availableDigits.add(new int[]{cost[digit], digit});
        }
        availableDigits.sort((a, b) -> {
            if (a[0] != b[0]) {
                return a[0] - b[0]; // Sort by cost ascending
            }
            return b[1] - a[1]; // Sort by digit descending
        });

        // dp[i] stores the maximum length of a number that can be formed with i matchsticks.
        // We use an array of strings to store the actual largest number.
        String[] dp = new String[N + 1];
        Arrays.fill(dp, ""); // Initialize with empty string, representing impossible to form.

        dp[0] = "0"; // Base case: 0 matchsticks can form an empty number (represented by "0" to indicate it's reachable)

        for (int i = 1; i <= N; i++) {
            for (int[] digitInfo : availableDigits) {
                int c = digitInfo[0]; // cost
                int d = digitInfo[1]; // digit

                if (i - c >= 0) {
                    String prevNum = dp[i - c];
                    if (!prevNum.equals("")) { // If a number can be formed with i-c matchsticks
                        StringBuilder currentNumBuilder = new StringBuilder();
                        if (prevNum.equals("0")) { // If prevNum was the base case "0", it means we are forming the first digit
                            currentNumBuilder.append(d);
                        } else {
                            currentNumBuilder.append(prevNum).append(d);
                        }
                        
                        String currentNum = currentNumBuilder.toString();

                        // Compare and update dp[i]
                        // If currentNum is longer, or same length but lexicographically larger
                        if (currentNum.length() > dp[i].length()) {
                            dp[i] = currentNum;
                        } else if (currentNum.length() == dp[i].length()) {
                            if (currentNum.compareTo(dp[i]) > 0) {
                                dp[i] = currentNum;
                            }
                        }
                    }
                }
            }
        }
        
        System.out.println(dp[N]);
    }
}