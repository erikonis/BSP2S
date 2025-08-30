import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] a = new int[N];
        for (int i = 0; i < N; i++) {
            a[i] = sc.nextInt();
        }
        sc.close();

        // The target integer 'y' must be within a reasonable range.
        // The problem constraints state a_i are between -100 and 100.
        // It can be shown that the optimal target integer 'y' will be
        // within the range of the given integers, or close to it.
        // A safe range to check would be from min(a_i) to max(a_i),
        // or even slightly outside, like -100 to 100, or -100 to 100 (inclusive).
        // For simplicity and safety, we can iterate from -100 to 100.
        // The problem statement implies 'y' is an integer.
        int minCost = Integer.MAX_VALUE;

        for (int y = -100; y <= 100; y++) {
            int currentCost = 0;
            for (int i = 0; i < N; i++) {
                currentCost += Math.pow(a[i] - y, 2);
            }
            minCost = Math.min(minCost, currentCost);
        }

        System.out.println(minCost);
    }
}