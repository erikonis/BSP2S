import java.util.*;
class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int N = sc.nextInt();
    int max = 0;
    int c = 0;
    for (int i = 0; i < N; i++) {
      int n = sc.nextInt();
      if (n >= max) {
        max = n;
      } else {
        c++;
      }
    }
    System.out.println(N - c);
  }
}