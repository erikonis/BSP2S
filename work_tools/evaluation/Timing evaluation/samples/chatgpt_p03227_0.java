import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String S = sc.nextLine();
        
        // Alternative: Use ternary operator and StringBuilder for reverse
        System.out.println(S.length() == 2 ? S : new StringBuilder(S).reverse().toString());
    }
}