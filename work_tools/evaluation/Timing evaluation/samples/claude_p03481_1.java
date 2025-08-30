import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long X = sc.nextLong();
        long Y = sc.nextLong();
        
        int length = 1; // Start with X itself
        long current = X;
        
        // Keep multiplying by 2 while staying within bounds
        while (current <= Y / 2) { // Use Y/2 to avoid overflow
            current *= 2;
            length++;
        }
        
        System.out.println(length);
    }
}