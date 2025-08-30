import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long A = sc.nextLong();
        long B = sc.nextLong();
        long N = sc.nextLong();
        
        long x = Math.min(N, B - 1);
        System.out.println((A * x) / B);
    }
}