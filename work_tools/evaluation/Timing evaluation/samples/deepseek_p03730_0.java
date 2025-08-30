import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int A = sc.nextInt();
        int B = sc.nextInt();
        int C = sc.nextInt();
        
        int gcd = gcd(A, B);
        if (C % gcd == 0) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
    
    private static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
}