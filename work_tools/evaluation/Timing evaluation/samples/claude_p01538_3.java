import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int Q = sc.nextInt();
        
        for (int i = 0; i < Q; i++) {
            long n = sc.nextLong();
            System.out.println(solve(n));
        }
        sc.close();
    }
    
    static int solve(long n) {
        Set<Long> visited = new HashSet<>();
        int steps = 0;
        
        while (n >= 10) {
            if (visited.contains(n)) {
                return -1; // infinite loop
            }
            visited.add(n);
            
            String s = String.valueOf(n);
            long maxProduct = 0;
            
            for (int i = 1; i < s.length(); i++) {
                long left = Long.parseLong(s.substring(0, i));
                long right = Long.parseLong(s.substring(i));
                maxProduct = Math.max(maxProduct, left * right);
            }
            
            n = maxProduct;
            steps++;
        }
        
        return steps;
    }
}