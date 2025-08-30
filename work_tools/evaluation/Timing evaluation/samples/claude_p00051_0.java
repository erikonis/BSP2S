import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        
        for (int i = 0; i < n; i++) {
            String input = sc.next();
            char[] digits = input.toCharArray();
            
            // Sort for maximum (descending)
            Arrays.sort(digits);
            StringBuilder maxStr = new StringBuilder();
            for (int j = digits.length - 1; j >= 0; j--) {
                maxStr.append(digits[j]);
            }
            
            // Sort for minimum (ascending)
            StringBuilder minStr = new StringBuilder();
            for (int j = 0; j < digits.length; j++) {
                minStr.append(digits[j]);
            }
            
            long max = Long.parseLong(maxStr.toString());
            long min = Long.parseLong(minStr.toString());
            
            System.out.println(max - min);
        }
        
        sc.close();
    }
}