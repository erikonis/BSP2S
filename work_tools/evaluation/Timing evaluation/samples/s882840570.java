import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {
  static long startTime = System.currentTimeMillis();
  static Scanner sc = new Scanner(System.in);
  static ArrayList<ArrayList<Integer>> nei = new ArrayList<>();
  static int N;
  static int[] parent, ord, n2i, dp;

  public static void main(String[] args) {
    N = sc.nextInt();
    parent = new int[N];
    ord = new int[N];
    n2i = new int[N];
    dp = new int[N];
    for (int i = 0; i < N; i++) {
      nei.add(new ArrayList<>());
    }
    for (int i = 0; i < N - 1; i++) {
      int A = Integer.parseInt(sc.next());
      int B = Integer.parseInt(sc.next());
      nei.get(A).add(B);
      nei.get(B).add(A);
    }
    ArrayList<Integer> cand = new ArrayList<>();
    ArrayList<Integer> other = new ArrayList<>();
    for (int i = 0; i < N; i++) {
      if (nei.get(i).size() == 1) {
        cand.add(i);
      } else {
        other.add(i);
      }
    }
    int ans = N;
    Collections.shuffle(cand);
    Collections.shuffle(other);
    for (int i = 0; i < cand.size(); i++) {
      if (System.currentTimeMillis() - startTime > 1800) break;
      int start = cand.get(i);
      ans = Math.min(ans, count(start));
    }
    for (int i = 0; i < other.size(); i++) {
      if (System.currentTimeMillis() - startTime > 1800) break;
      int start = other.get(i);
      ans = Math.min(ans, count(start));
    }
    System.out.println(ans);
  }

  static int count(int s) {
    ord[0] = s;
    n2i[s] = 0;
    parent[0] = -1;
    int pos = 1;
    for (int i = 0; i < N; i++) {
      for (int n : nei.get(ord[i])) {
        if (n != parent[i]) {
          ord[pos] = n;
          n2i[n] = pos;
          parent[pos] = ord[i];
          ++pos;
        }
      }
    }
    Arrays.fill(dp, 0);
    int ret = 1;
    for (int i = N - 1; i >= 0; i--) {
      int cur = ord[i];
      dp[i] = 0;
      int ec = 0;
      for (int n : nei.get(cur)) {
        if (n == parent[i]) continue;
        if (dp[n2i[n]] == 0) {
          ++ec;
        } else {
          dp[i] += dp[n2i[n]];
        }
      }
      if (ec > 1) dp[i] += ec - 1;
    }
    return dp[0] + 1;
  }

}
