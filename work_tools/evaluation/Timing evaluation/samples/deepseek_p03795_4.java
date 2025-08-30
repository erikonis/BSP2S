import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int x = N * 800;
        int y = (N / 15) * 200;
        System.out.println(x - y);
    }
}