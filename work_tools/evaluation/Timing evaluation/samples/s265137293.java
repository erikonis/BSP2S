import java.util.*;

public class Main {

  static Scanner sc = new Scanner(System.in);

  static int N;
  static ArrayList<Integer> original;

  public static void main(String[] args) {
    N = sc.nextInt();
    int A = sc.nextInt();
    int B = sc.nextInt();
    int C = sc.nextInt();

    original = new ArrayList(N);

    for (int i=0; i<N; i++) {
      original.add(sc.nextInt());
    }

    int minMP = Integer.MAX_VALUE;

    minMP = Math.min(minMP, g(new ArrayList(original), A, B, C));
    minMP = Math.min(minMP, g(new ArrayList(original), A, C, B));
    minMP = Math.min(minMP, g(new ArrayList(original), B, A, C));
    minMP = Math.min(minMP, g(new ArrayList(original), B, C, A));
    minMP = Math.min(minMP, g(new ArrayList(original), C, A, B));
    minMP = Math.min(minMP, g(new ArrayList(original), C, B, A));

    System.out.println(minMP);

  }

  static int h(ArrayList<Integer> ls, int len) {
    int index = -1;
    int minDif = Integer.MAX_VALUE;

    for (int i = 0; i < ls.size(); i++) {
      int dif = Math.abs(ls.get(i) - len);
      if (dif < minDif) {
        index = i;
        minDif = dif;
      }
    }

    ls.remove(index);

    return minDif;

  }

  static int g(ArrayList<Integer> ls, int a, int b, int c) {

    int min = Integer.MAX_VALUE;

    if (ls.size() > 3) {
      for (int i = 0; i < ls.size(); i++) {
        for (int j = i + 1; j < ls.size(); j++) {
          ArrayList<Integer> copyList = new ArrayList(ls);
          copyList.set(i, copyList.get(i) + copyList.get(j));
          copyList.remove(j);
          min = Math.min(min, 10 + g(copyList, a, b, c));
        }
      }
    }

    return Math.min(min, h(ls, a) + h(ls, b) + h(ls, c));
  }

}