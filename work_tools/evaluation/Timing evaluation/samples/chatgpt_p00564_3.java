import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int A = sc.nextInt();
        int B = sc.nextInt();
        int C = sc.nextInt();
        int D = sc.nextInt();

        // Sets needed for X and Y
        int setX = (N + A - 1) / A;
        int setY = (N + C - 1) / C;

        // Total cost for X and Y
        int totalX = setX * B;
        int totalY = setY * D;

        System.out.println(Math.min(totalX, totalY));
    }
}