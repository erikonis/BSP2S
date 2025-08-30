import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            int a1 = sc.nextInt();
            int m1 = sc.nextInt();
            int a2 = sc.nextInt();
            int m2 = sc.nextInt();
            int a3 = sc.nextInt();
            int m3 = sc.nextInt();
            
            if (a1 == 0 && m1 == 0 && a2 == 0 && m2 == 0 && a3 == 0 && m3 == 0) {
                break;
            }
            
            // Find period for each dimension
            long period1 = findPeriod(a1, m1);
            long period2 = findPeriod(a2, m2);
            long period3 = findPeriod(a3, m3);
            
            // Find LCM of all three periods
            long result = lcm(lcm(period1, period2), period3);
            System.out.println(result);
        }
        
        sc.close();
    }
    
    private static long findPeriod(int a, int m) {
        int x = 1;
        for (long step = 1; ; step++) {
            x = (int)((long)a * x % m);
            if (x == 1) {
                return step;
            }
        }
    }
    
    private static long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    
    private static long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }
}