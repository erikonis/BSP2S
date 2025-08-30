import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String S = sc.next();
        boolean easy = true;
        
        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            if ((i + 1) % 2 == 1) {
                if (c != 'R' && c != 'U' && c != 'D') {
                    easy = false;
                    break;
                }
            } else {
                if (c != 'L' && c != 'U' && c != 'D') {
                    easy = false;
                    break;
                }
            }
        }
        System.out.println(easy ? "Yes" : "No");
    }
}