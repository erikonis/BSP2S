import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();
        String S = sc.next();
        sc.close();

        int happyCount = 0;
        for (int i = 0; i < N - 1; i++) {
            if (S.charAt(i) == S.charAt(i + 1)) {
                happyCount++;
            }
        }

        int blocks = 0;
        for (int i = 0; i < N - 1; i++) {
            if (S.charAt(i) != S.charAt(i + 1)) {
                blocks++;
            }
        }

        int maxHappy = Math.min(N - 1, happyCount + 2 * K);

        System.out.println(maxHappy);
    }
}