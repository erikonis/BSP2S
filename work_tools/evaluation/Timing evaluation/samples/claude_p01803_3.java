import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            int n = sc.nextInt();
            if (n == 0) break;
            
            String[] airports = new String[n];
            for (int i = 0; i < n; i++) {
                airports[i] = sc.next();
            }
            
            // Generate code strings for each airport
            String[] codes = new String[n];
            for (int i = 0; i < n; i++) {
                codes[i] = generateCodeString(airports[i]);
            }
            
            int result = findMinK(codes);
            System.out.println(result);
        }
        
        sc.close();
    }
    
    private static String generateCodeString(String name) {
        StringBuilder code = new StringBuilder();
        Set<Character> vowels = Set.of('a', 'i', 'u', 'e', 'o');
        
        // Add first character
        code.append(name.charAt(0));
        
        // Add characters after vowels
        for (int i = 0; i < name.length() - 1; i++) {
            if (vowels.contains(name.charAt(i))) {
                code.append(name.charAt(i + 1));
            }
        }
        
        return code.toString();
    }
    
    private static int findMinK(String[] codes) {
        int maxLength = 0;
        for (String code : codes) {
            maxLength = Math.max(maxLength, code.length());
        }
        
        for (int k = 1; k <= maxLength; k++) {
            Set<String> usedCodes = new HashSet<>();
            boolean valid = true;
            
            for (String code : codes) {
                String truncated = code.length() >= k ? code.substring(0, k) : code;
                if (usedCodes.contains(truncated)) {
                    valid = false;
                    break;
                }
                usedCodes.add(truncated);
            }
            
            if (valid) {
                return k;
            }
        }
        
        return -1;
    }
}