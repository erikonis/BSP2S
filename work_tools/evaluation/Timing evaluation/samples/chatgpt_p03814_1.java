import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        int n = s.length();
        int maxLen = 0;

        // Find all positions of 'A'
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == 'A') {
                // For each 'A', search for the farthest 'Z' to the right
                for (int j = n - 1; j >= i; j--) {
                    if (s.charAt(j) == 'Z') {
                        int len = j - i + 1;
                        if (len > maxLen) maxLen = len;
                        break; // found the farthest Z for this A
                    }
                }
            }
        }
        System.out.println(maxLen);
    }
}