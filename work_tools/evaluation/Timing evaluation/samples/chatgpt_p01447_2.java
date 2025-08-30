import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long N = sc.nextLong();
        int ans = 0;
        long p = 1;
        while (p < N) {
            p *= 3;
            ans++;
        }
        System.out.println(ans);
    }
}