import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

  int cnt = 0;

  public static Integer[] getG(int n) {
    // via https://stackoverflow.com/questions/2539545/fastest-gap-sequence-for-shell-sort
    int[] optimal = {1, 4, 10, 23, 57, 132, 301, 701, 1750};
    List<Integer> G = new ArrayList<Integer>();
    int i = 0;
    int num = optimal[i];
    G.add(num);
    while (num < n) {
      i++;
      if (i < optimal.length) {
        num = optimal[i];
      } else {
        num = (int)(num * 2.25);
      }
      if (num < n)
        G.add(num);
    }
    Collections.reverse(G);
    Integer[] G_ = new Integer[G.size()];
    G_ = G.toArray(G_);
    return G_;
  }

  public static void display(Integer[] a) {
    for (int i = 0; i < a.length; i++) {
      System.out.print(a[i]);
      if (i != a.length - 1)
        System.out.print(" ");
      else
        System.out.print("\n");
    }
  }

  public int[] insertionSort(int[] A, int n, int g) {

    for (int i = g; i < n; i++) {
      int v = A[i];
      int j = i - g;
      while (j >= 0 && A[j] > v) {
        A[j+g] = A[j];
        j = j - g;
        this.cnt++;
      }
      A[j + g] = v;
    }

    return A;
  }

  public int[] shellSort(int[] A, int n) {

    Integer[] G = getG(n);
    int m = G.length;

    // Display for aizu
    System.out.println(m);
    display(G);

    int[] result = new int[0];
    for (int i = 0; i < m; i++) {
      result = this.insertionSort(A, n, G[i]);
    }

    System.out.println(this.cnt);

    return result;
  }

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
    int n = s.nextInt();
    int[] A = new int[n];
    for (int i = 0; i < n; i++) {
      A[i] = s.nextInt();
    }
    Main x = new Main();
    int[] result = x.shellSort(A, n);
    for (int i = 0; i < result.length; i++) {
      System.out.println(result[i]);
    }
	}
}

