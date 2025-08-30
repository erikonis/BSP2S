import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String S = sc.next();

        // Use a boolean array to mark presence of each letter
        boolean[] present = new boolean[26];

        // Alternative: Use String.indexOf for each letter
        for (char c = 'a'; c <= 'z'; c++) {
            if (S.indexOf(c) == -1) {
                System.out.println(c);
                return;
            }
        }
        System.out.println("None");
    }
}