import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int count = 1;
        while (true) {
            int x = sc.nextInt();
            if (x == 0) break;
            System.out.println("Case " + count + ": " + x);
            count++;
        }
    }
}