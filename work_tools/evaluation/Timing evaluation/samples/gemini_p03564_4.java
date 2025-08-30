import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();
        sc.close();

        int currentMin = 1; // Start with 1 as specified

        // Iterate N times, applying the operation that yields the smaller value
        for (int i = 0; i < N; i++) {
            int opAValue = currentMin * 2; // Value if Operation A is performed
            int opBValue = currentMin + K; // Value if Operation B is performed

            // Choose the operation that results in a smaller value
            if (opAValue < opBValue) {
                currentMin = opAValue;
            } else {
                currentMin = opBValue;
            }
        }

        System.out.println(currentMin);
    }
}