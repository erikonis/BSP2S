import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int currentExpected = 1;
        int remainingBricks = 0;

        for (int i = 0; i < N; i++) {
            int a = sc.nextInt();
            if (a == currentExpected) {
                currentExpected++;
                remainingBricks++;
            }
        }

        if (remainingBricks == 0) {
            System.out.println(-1);
        } else {
            System.out.println(N - remainingBricks);
        }

        sc.close();
    }
}