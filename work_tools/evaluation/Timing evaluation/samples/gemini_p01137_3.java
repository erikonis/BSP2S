import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int e = sc.nextInt();
            if (e == 0) {
                break;
            }

            int minSum = Integer.MAX_VALUE;

            // Iterate through possible values of z^3
            // z^3 <= e => z <= e^(1/3)
            for (int z = 0; z * z * z <= e; z++) {
                int eMinusZ3 = e - z * z * z;
                // Iterate through possible values of y^2
                // y^2 <= e - z^3 => y <= sqrt(e - z^3)
                for (int y = 0; y * y <= eMinusZ3; y++) {
                    int x = eMinusZ3 - y * y;
                    // x must be non-negative, which is guaranteed by the loop conditions
                    // (e - z^3 - y^2) >= 0
                    minSum = Math.min(minSum, x + y + z);
                }
            }
            System.out.println(minSum);
        }
        sc.close();
    }
}