import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] a = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            a[i] = sc.nextInt();
        }

        int currentButton = 1;
        int presses = 0;
        // Keep track of visited buttons to detect cycles
        boolean[] visited = new boolean[N + 1];

        while (currentButton != 2) {
            if (visited[currentButton]) {
                // If we encounter a button we've already visited, it means we're in a cycle
                // and Button 2 is not reachable.
                System.out.println("-1");
                return;
            }
            visited[currentButton] = true;
            currentButton = a[currentButton];
            presses++;
        }

        System.out.println(presses);
        sc.close();
    }
}