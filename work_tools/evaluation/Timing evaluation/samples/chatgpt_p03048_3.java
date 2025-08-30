import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int R = sc.nextInt();
        int G = sc.nextInt();
        int B = sc.nextInt();
        int N = sc.nextInt();

        int ans = 0;
        for (int r = 0; r <= N / R; r++) {
            for (int g = 0; g <= N / G; g++) {
                int rem = N - (R * r + G * g);
                if (rem < 0) continue;
                if (rem % B == 0) {
                    ans++;
                }
            }
        }
        System.out.println(ans);
    }
}