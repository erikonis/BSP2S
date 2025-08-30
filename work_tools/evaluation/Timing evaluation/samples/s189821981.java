import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int N = scanner.nextInt();
    int[] count = new int[N];
    int M = scanner.nextInt();
    for (int i = 0; i < M; i++) {
      int a = scanner.nextInt() - 1;
      int b = scanner.nextInt() - 1;
      count[a]++;
      count[b]++;
    }
    for (int i = 0; i < N; i++) {
      if (count[i] % 2 != 0) {
        System.out.println("NO");
        return;
      }
    }
    System.out.println("YES");
  }
}
