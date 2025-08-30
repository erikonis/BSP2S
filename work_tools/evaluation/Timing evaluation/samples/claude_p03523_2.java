import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        
        String target = "AKIHABARA";
        
        // Remove all A's from target to get required subsequence
        StringBuilder nonA = new StringBuilder();
        for (char c : target.toCharArray()) {
            if (c != 'A') {
                nonA.append(c);
            }
        }
        String requiredSubseq = nonA.toString(); // "KIHBR"
        
        // Check if s is subsequence of target
        int i = 0; // pointer for s
        int j = 0; // pointer for target
        
        while (i < s.length() && j < target.length()) {
            if (s.charAt(i) == target.charAt(j)) {
                i++;
            }
            j++;
        }
        
        // s should be completely matched and should equal requiredSubseq when A's removed
        if (i == s.length()) {
            // Remove A's from s and check if it equals requiredSubseq
            StringBuilder sWithoutA = new StringBuilder();
            for (char c : s.toCharArray()) {
                if (c != 'A') {
                    sWithoutA.append(c);
                }
            }
            
            if (sWithoutA.toString().equals(requiredSubseq)) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        } else {
            System.out.println("NO");
        }
    }
}