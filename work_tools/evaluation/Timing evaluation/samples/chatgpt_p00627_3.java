import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int n = Integer.parseInt(sc.nextLine());
            if (n == 0) break;
            int m = n / 4;
            int total = 0;
            for (int i = 0; i < m; i++) {
                total += Integer.parseInt(sc.nextLine());
            }
            System.out.println(total);
        }
    }
}