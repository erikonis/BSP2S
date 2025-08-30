import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        int countA = 0, countB = 0, countC = 0;
        
        for (char c : s.toCharArray()) {
            if (c == 'a') countA++;
            else if (c == 'b') countB++;
            else if (c == 'c') countC++;
        }
        
        System.out.println((countA == 1 && countB == 1 && countC == 1) ? "Yes" : "No");
    }
}