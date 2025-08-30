import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int i = sc.nextInt();
        // The j-th car from the back is (N - i + 1)
        System.out.println(N - i + 1);
    }
}