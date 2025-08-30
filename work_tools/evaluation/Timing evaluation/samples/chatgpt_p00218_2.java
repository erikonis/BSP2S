import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int n = sc.nextInt();
            if (n == 0) break;
            for (int i = 0; i < n; i++) {
                int m = sc.nextInt();
                int e = sc.nextInt();
                int j = sc.nextInt();

                // 条件判定
                if (m == 100 || e == 100 || j == 100) {
                    System.out.println("A");
                } else if (((m + e) / 2) >= 90) {
                    System.out.println("A");
                } else {
                    int avg = (m + e + j) / 3;
                    if (avg >= 80) {
                        System.out.println("A");
                    } else if (avg >= 70) {
                        System.out.println("B");
                    } else if (avg >= 50 && (m >= 80 || e >= 80)) {
                        System.out.println("B");
                    } else {
                        System.out.println("C");
                    }
                }
            }
        }
    }
}