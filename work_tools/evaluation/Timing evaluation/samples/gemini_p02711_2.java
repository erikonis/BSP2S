import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int N = sc.nextInt();

    if (String.valueOf(N).indexOf('7') != -1) {
      System.out.println("Yes");
    } else {
      System.out.println("No");
    }

    sc.close();
  }
}