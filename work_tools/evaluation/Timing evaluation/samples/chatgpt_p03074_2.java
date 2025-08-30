import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(), K = sc.nextInt();
        String S = sc.next();
        // Store runs of 1s and 0s as intervals
        ArrayList<int[]> runs = new ArrayList<>();
        int curr = S.charAt(0) - '0';
        int count = 1;
        for (int i = 1; i < N; i++) {
            int val = S.charAt(i) - '0';
            if (val == curr) {
                count++;
            } else {
                runs.add(new int[]{curr, count});
                curr = val;
                count = 1;
            }
        }
        runs.add(new int[]{curr, count});
        // Two pointers over the runs array
        int max = 0, left = 0, right = 0, flips = 0, len = runs.size();
        int sum = 0;
        while (right < len) {
            // If run is 0, flipping it (so increases flip count)
            if (runs.get(right)[0] == 0) flips++;
            sum += runs.get(right)[1];
            // If flips > K, shrink window from left
            while (flips > K) {
                if (runs.get(left)[0] == 0) flips--;
                sum -= runs.get(left)[1];
                left++;
            }
            max = Math.max(max, sum);
            right++;
        }
        System.out.println(max);
    }
}