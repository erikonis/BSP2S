import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int x = sc.nextInt();
    String answer = "";
    if (x >= 1800) {
      answer = "1";
    } else if (x >= 1600) {
      answer = "2";
    } else if (x >= 1400) {
      answer = "3";
    } else if (x >= 1200) {
      answer = "4";
    } else if (x >= 1000) {
      answer = "5";
    } else if (x >= 800) {
      answer = "6";
    } else if (x >= 600) {
      answer = "7";
    } else if (x >= 400) {
      answer = "8";
    }
    System.out.print(answer);
  }
}