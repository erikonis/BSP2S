import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        
        // Find leftmost A
        int leftA = -1;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'A') {
                leftA = i;
                break;
            }
        }
        
        // Find rightmost Z
        int rightZ = -1;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == 'Z') {
                rightZ = i;
                break;
            }
        }
        
        // Calculate length
        int length = rightZ - leftA + 1;
        System.out.println(length);
    }
}