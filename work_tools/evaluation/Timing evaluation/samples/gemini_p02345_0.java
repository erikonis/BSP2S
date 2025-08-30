import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    private static final int INF = Integer.MAX_VALUE;
    private int[] tree;
    private int n;

    public Main(int size) {
        n = 1;
        while (n < size) {
            n *= 2;
        }
        tree = new int[2 * n - 1];
        Arrays.fill(tree, INF);
    }

    public void update(int i, int x) {
        i += n - 1;
        tree[i] = x;
        while (i > 0) {
            i = (i - 1) / 2;
            tree[i] = Math.min(tree[i * 2 + 1], tree[i * 2 + 2]);
        }
    }

    public int find(int s, int t) {
        return query(s, t + 1, 0, 0, n);
    }

    private int query(int a, int b, int k, int l, int r) {
        if (r <= a || b <= l) {
            return INF;
        }
        if (a <= l && r <= b) {
            return tree[k];
        }
        int vl = query(a, b, k * 2 + 1, l, (l + r) / 2);
        int vr = query(a, b, k * 2 + 2, (l + r) / 2, r);
        return Math.min(vl, vr);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] line = br.readLine().split(" ");
        int n = Integer.parseInt(line[0]);
        int q = Integer.parseInt(line[1]);

        Main rmq = new Main(n);

        for (int k = 0; k < q; k++) {
            line = br.readLine().split(" ");
            int com = Integer.parseInt(line[0]);
            int x = Integer.parseInt(line[1]);
            int y = Integer.parseInt(line[2]);

            if (com == 0) {
                rmq.update(x, y);
            } else {
                System.out.println(rmq.find(x, y));
            }
        }
    }
}