import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        String s = sc.next();
        sc.close();

        // Calculate the count difference directly
        int redCount = 0;
        for (char c : s.toCharArray()) {
            if (c == 'R') {
                redCount++;
            } else { // c == 'B'
                redCount--; // Decrement for blue hats
            }
        }

        // If redCount > 0, it means there are more red hats than blue hats
        if (redCount > 0) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }
}