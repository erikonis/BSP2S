import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        
        // Check condition 1: first character is 'A'
        if (s.charAt(0) != 'A') {
            System.out.println("WA");
            return;
        }
        
        // Check condition 2: exactly one 'C' between index 2 and n-2 (inclusive)
        int cCount = 0;
        for (int i = 2; i <= s.length() - 2; i++) {
            if (s.charAt(i) == 'C') {
                cCount++;
            }
        }
        
        if (cCount != 1) {
            System.out.println("WA");
            return;
        }
        
        // Check condition 3: all other characters are lowercase
        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            // Skip the valid 'C' position
            if (c == 'C' && i >= 2 && i <= s.length() - 2) {
                continue;
            }
            // Check if character is not lowercase
            if (c < 'a' || c > 'z') {
                System.out.println("WA");
                return;
            }
        }
        
        System.out.println("AC");
    }
}