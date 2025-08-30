import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        int[] b = new int[n];
        
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        for (int i = 0; i < n; i++) {
            b[i] = sc.nextInt();
        }
        
        int result = 0;
        
        // Check each bit position (up to 30 bits should be enough)
        for (int bit = 0; bit < 30; bit++) {
            int count = 0;
            
            // For each pair (i,j), check if bit 'bit' is set in a[i] + b[j]
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (((a[i] + b[j]) >> bit) % 2 == 1) {
                        count++;
                    }
                }
            }
            
            // If count is odd, set this bit in result
            if (count % 2 == 1) {
                result |= (1 << bit);
            }
        }
        
        System.out.println(result);
    }
}