import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] A = new int[N + 1];
        for (int i = 1; i <= N; i++) A[i] = sc.nextInt();

        long ans = 0;
        Map<Integer, Integer> countSum = new HashMap<>();
        Map<Integer, Integer> countDiff = new HashMap<>();

        for (int i = 1; i <= N; i++) {
            int keySum = i + A[i];
            int keyDiff = i - A[i];

            ans += countSum.getOrDefault(keyDiff, 0);
            ans += countDiff.getOrDefault(keySum, 0);

            countSum.put(keySum, countSum.getOrDefault(keySum, 0) + 1);
            countDiff.put(keyDiff, countDiff.getOrDefault(keyDiff, 0) + 1);
        }
        // Each pair is counted twice, so divide by 2
        System.out.println(ans / 2);
    }
}