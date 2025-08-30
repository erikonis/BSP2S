import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String N = sc.next();
        long sum = 0;
        for (char c : N.toCharArray()) {
            sum += Character.getNumericValue(c);
        }
        if (sum % 9 == 0) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
        sc.close();
    }
}