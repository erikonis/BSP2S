import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int A = sc.nextInt();
        int B = sc.nextInt();
        int K = sc.nextInt();
        
        List<Integer> divisors = new ArrayList<>();
        int gcd = gcd(A, B);
        
        for (int i = 1; i <= gcd; i++) {
            if (gcd % i == 0) {
                divisors.add(i);
            }
        }
        
        Collections.sort(divisors, Collections.reverseOrder());
        System.out.println(divisors.get(K - 1));
    }
    
    private static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
}