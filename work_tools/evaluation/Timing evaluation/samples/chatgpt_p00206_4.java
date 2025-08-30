import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int L = sc.nextInt();
            if (L == 0) break;

            int[] M = new int[12];
            int[] N = new int[12];
            for (int i = 0; i < 12; i++) {
                M[i] = sc.nextInt();
                N[i] = sc.nextInt();
            }

            int sum = 0;
            int res = -1;
            for (int i = 0; i < 12; i++) {
                sum += M[i] - N[i];
                if (sum >= L) {
                    res = i + 1;
                    break;
                }
            }
            if (res == -1) {
                System.out.println("NA");
            } else {
                System.out.println(res);
            }
        }
    }
}