import java.io.InputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class Main {

  public static void main(String[] args) {
    solve(System.in, System.out);
  }

  private static class Edge {

    public Edge(int fr, int to) {
      this.fr = fr;
      this.to = to;
    }

    int fr;
    int to;
    int color;
  }

  static void solve(InputStream is, PrintStream os) {
    Scanner sc = new Scanner(is);

    /* read */
    int n = sc.nextInt();

    Map<Integer, Set<Edge>> graph = new HashMap<>();

    for (int i = 0; i < n; i++) {
      graph.put(i, new HashSet<>());
    }

    List<Edge> edges = new ArrayList<>();
    for (int i = 0; i < n - 1; i++) {
      int a = sc.nextInt() - 1;
      int b = sc.nextInt() - 1;
      Edge e = new Edge(a, b);
      graph.get(a).add(e);
      edges.add(e);
    }

    dfs(0, graph, 0);

    int max = 0;
    for (Edge q : edges) {
      max = Math.max(max, q.color);
    }
    os.println(max);
    for (Edge q : edges) {
      os.println(q.color);
    }
  }

  private static void dfs(int start, Map<Integer, Set<Edge>> graph, int incomingColor) {
    int color = 1;
    for (Edge e : graph.get(start)) {
      if (color == incomingColor) {
        color++;
      }
      int child = e.to;
      e.color = color;
      dfs(child, graph, color);
      color++;
    }
  }

  private static class Scanner {

    private final InputStream is;
    private final byte[] buffer = new byte[1024];
    private int ptr = 0;
    private int buflen = 0;

    Scanner(InputStream is) {
      this.is = is;
    }

    private boolean hasNextByte() {
      if (ptr < buflen) {
        return true;
      } else {
        ptr = 0;
        try {
          buflen = is.read(buffer);
        } catch (IOException e) {
          e.printStackTrace();
        }
        if (buflen <= 0) {
          return false;
        }
      }
      return true;
    }

    private int readByte() {
      if (hasNextByte()) {
        return buffer[ptr++];
      } else {
        return -1;
      }
    }

    private static boolean isPrintableChar(int c) {
      return 33 <= c && c <= 126;
    }

    public boolean hasNext() {
      while (hasNextByte() && !isPrintableChar(buffer[ptr])) {
        ptr++;
      }
      return hasNextByte();
    }

    public String next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      StringBuilder sb = new StringBuilder();
      int b = readByte();
      while (isPrintableChar(b)) {
        sb.appendCodePoint(b);
        b = readByte();
      }
      return sb.toString();
    }

    public long nextLong() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      long n = 0;
      boolean minus = false;
      int b = readByte();
      if (b == '-') {
        minus = true;
        b = readByte();
      }
      if (b < '0' || '9' < b) {
        throw new NumberFormatException();
      }
      while (true) {
        if ('0' <= b && b <= '9') {
          n *= 10;
          n += b - '0';
        } else if (b == -1 || !isPrintableChar(b)) {
          return minus ? -n : n;
        } else {
          throw new NumberFormatException();
        }
        b = readByte();
      }
    }

    public int nextInt() {
      long nl = nextLong();
      if (nl < Integer.MIN_VALUE || nl > Integer.MAX_VALUE) {
        throw new NumberFormatException();
      }
      return (int) nl;
    }

    public double nextDouble() {
      return Double.parseDouble(next());
    }
  }
}
