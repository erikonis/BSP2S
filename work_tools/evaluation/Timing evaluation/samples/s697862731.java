import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    int N = scanner.nextInt();
    int M = scanner.nextInt();
    long[] enter = new long[N + 1];
    List<int[]>[] exit = new List[N + 2];
    for (int i = 0; i <= N + 1; i++) exit[i] = new ArrayList<>();

    for (int i = 0; i < M; i++) {
      int left = scanner.nextInt();
      int right = scanner.nextInt();
      int a = scanner.nextInt();
      enter[left] += a;
      exit[right + 1].add(new int[]{left, right, a});
    }

    SegmentTree st = new SegmentTree(N);
    long max = 0;
    for (int i = 1; i <= N; i++) {
      st.add(0, i, enter[i]);
      for (int[] e : exit[i]) st.add(0, e[0], -e[2]);
      long dp = st.query(0, i);
      st.add(i, i + 1, dp);
      max = Math.max(max, dp);
    }
    System.out.println(max);
  }

  private static class SegmentTree {
    private static final long INF = -1_000_000_000_000_000L;
    private final int n;
    long[] max;
    long[] add;
    public SegmentTree(int n) {
      int tmpn = 1;
      while (tmpn < n) tmpn <<= 1;
      this.n = tmpn;
      max = new long[this.n * 2 - 1];
      add = new long[this.n * 2 - 1];
    }

    public long query(int a, int b) {
      return query(a, b, 0, 0, n);
    }

    private long query(int a, int b, int k, int l, int r) {
      if (b <= l || a >= r) return INF;
      if (a <= l && r <= b) return max[k];
      long lv = query(a, b, k * 2 + 1, l, (l + r) / 2);
      long rv = query(a, b, k * 2 + 2, (l + r) / 2, r);
      return Math.max(lv, rv) + add[k];
    }

    public void add(int a, int b, long v) {
      add(a, b, v, 0, 0, n);
    }

    private void add(int a, int b, long v, int k, int l, int r) {
      if (a <= l && r <= b) {
        add[k] += v;
        max[k] += v;
        return;
      }
      if (b <= l || a >= r) return;
      add(a, b, v, k * 2 + 1, l, (l + r) / 2);
      add(a, b, v, k * 2 + 2, (l + r) / 2, r);
      max[k] = Math.max(max[k * 2 + 1], max[k * 2 + 2]) + add[k];
    }
  }
}
