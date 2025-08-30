import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long X = sc.nextLong();
        long Y = sc.nextLong();
        
        // Base cases
        if (X == 0 && Y == 0) {
            System.out.println("Brown");
            return;
        }
        
        if (X == 0 || Y == 0) {
            System.out.println("Alice");
            return;
        }
        
        // Euclidean game analysis
        // Alice wins if the position is not balanced
        // This happens when max(X,Y) > 2*min(X,Y)
        long max = Math.max(X, Y);
        long min = Math.min(X, Y);
        
        if (max > 2 * min) {
            System.out.println("Alice");
        } else {
            System.out.println("Brown");
        }
    }
}