import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();
        int c = sc.nextInt();
        int d = sc.nextInt();

        boolean directCommunicate = Math.abs(a - c) <= d;
        boolean indirectCommunicate = Math.abs(a - b) <= d && Math.abs(b - c) <= d;

        if (directCommunicate || indirectCommunicate) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }

        sc.close();
    }
}