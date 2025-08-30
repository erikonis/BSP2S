import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      int n = scanner.nextInt();
      int m = scanner.nextInt();
      int a = scanner.nextInt();

      if (n == 0 && m == 0 && a == 0) {
        break;
      }

      List<Ladder> ladders = new ArrayList<>();
      for (int i = 0; i < m; i++) {
        int h = scanner.nextInt();
        int p = scanner.nextInt();
        int q = scanner.nextInt();
        ladders.add(new Ladder(h, p, q));
      }

      Collections.sort(ladders);

      int currentPosition = a;
      for (Ladder ladder : ladders) {
        if (ladder.p == currentPosition) {
          currentPosition = ladder.q;
        } else if (ladder.q == currentPosition) {
          currentPosition = ladder.p;
        }
      }
      System.out.println(currentPosition);
    }

    scanner.close();
  }

  static class Ladder implements Comparable<Ladder> {

    int h;
    int p;
    int q;

    public Ladder(int h, int p, int q) {
      this.h = h;
      this.p = p;
      this.q = q;
    }

    @Override
    public int compareTo(Ladder other) {
      return Integer.compare(this.h, other.h);
    }
  }
}