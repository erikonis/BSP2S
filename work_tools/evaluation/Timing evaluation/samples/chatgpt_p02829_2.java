import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int A = sc.nextInt();
        int B = sc.nextInt();

        for (int i = 1; i <= 3; i++) {
            if (i != A && i != B) {
                System.out.println(i);
                break;
            }
        }
    }
}