import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int s = sc.nextInt();
        HashSet<Integer> seen = new HashSet<>();
        int m = 1;
        int current = s;
        seen.add(current);
        
        while (true) {
            m++;
            current = f(current);
            if (seen.contains(current)) {
                System.out.println(m);
                break;
            }
            seen.add(current);
        }
    }
    
    private static int f(int n) {
        return n % 2 == 0 ? n / 2 : 3 * n + 1;
    }
}