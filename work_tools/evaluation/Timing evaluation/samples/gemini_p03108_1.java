import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();

        int[][] bridges = new int[M][2];
        for (int i = 0; i < M; i++) {
            bridges[i][0] = sc.nextInt();
            bridges[i][1] = sc.nextInt();
        }

        long[] inconveniences = new long[M];
        DSU dsu = new DSU(N + 1);
        long currentInconvenience = 0;

        // Process bridges in reverse order
        for (int i = M - 1; i >= 0; i--) {
            inconveniences[i] = currentInconvenience;
            int u = bridges[i][0];
            int v = bridges[i][1];

            if (dsu.find(u) != dsu.find(v)) {
                long sizeU = dsu.getSize(u);
                long sizeV = dsu.getSize(v);
                currentInconvenience -= sizeU * (sizeU - 1) / 2;
                currentInconvenience -= sizeV * (sizeV - 1) / 2;
                dsu.union(u, v);
                long newSize = dsu.getSize(u);
                currentInconvenience += newSize * (newSize - 1) / 2;
            }
        }

        for (long inc : inconveniences) {
            System.out.println(inc);
        }
        sc.close();
    }
}

class DSU {
    int[] parent;
    int[] size;

    public DSU(int n) {
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int find(int i) {
        if (parent[i] == i) {
            return i;
        }
        return parent[i] = find(parent[i]);
    }

    public void union(int i, int j) {
        int rootI = find(i);
        int rootJ = find(j);
        if (rootI != rootJ) {
            if (size[rootI] < size[rootJ]) {
                int temp = rootI;
                rootI = rootJ;
                rootJ = temp;
            }
            parent[rootJ] = rootI;
            size[rootI] += size[rootJ];
        }
    }

    public int getSize(int i) {
        return size[find(i)];
    }
}