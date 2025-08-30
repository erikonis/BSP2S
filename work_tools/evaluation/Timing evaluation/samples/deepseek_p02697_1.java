import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();

        int left = 1;
        int right = N - 1;
        for (int i = 0; i < M; i++) {
            System.out.println(left + " " + right);
            left++;
            right--;
            if (left >= right) {
                right += N;
            }
        }
    }
}