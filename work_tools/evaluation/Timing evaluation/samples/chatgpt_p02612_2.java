import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int change = ((N + 999) / 1000) * 1000 - N;
        System.out.println(change);
    }
}