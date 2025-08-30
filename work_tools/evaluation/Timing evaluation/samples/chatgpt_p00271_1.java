import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 7; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            System.out.println(a - b);
        }
    }
}