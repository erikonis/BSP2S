import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int A = sc.nextInt(), B = sc.nextInt(), C = sc.nextInt(), K = sc.nextInt();
        for (int x = 0; x <= K; x++) {
            for (int y = 0; y <= K - x; y++) {
                int z = K - x - y;
                int a = A << x, b = B << y, c = C << z;
                if (b > a && c > b) {
                    System.out.println("Yes");
                    return;
                }
            }
        }
        System.out.println("No");
    }
}