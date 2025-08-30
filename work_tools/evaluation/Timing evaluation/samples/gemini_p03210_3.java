import java.util.Scanner;

public class ShichiGoSan {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt();

        // Check if Takahashi's age is 3, 5, or 7
        if (x == 3 || x == 5 || x == 7) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }

        sc.close();
    }
}