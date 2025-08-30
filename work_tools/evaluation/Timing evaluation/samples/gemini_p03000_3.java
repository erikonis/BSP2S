import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int X = sc.nextInt();
        int[] L = new int[N];
        for (int i = 0; i < N; i++) {
            L[i] = sc.nextInt();
        }

        int count = 0;
        int currentCoordinate = 0;

        // First bounce at D_1 = 0
        if (currentCoordinate <= X) {
            count++;
        }

        // Subsequent bounces
        for (int i = 0; i < N; i++) {
            currentCoordinate += L[i];
            if (currentCoordinate <= X) {
                count++;
            } else {
                // Since L_i are positive, coordinates will only increase.
                // If currentCoordinate > X, all subsequent coordinates will also be > X.
                break;
            }
        }

        System.out.println(count);

        sc.close();
    }
}