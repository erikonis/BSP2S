import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine().trim());

        if ((N & (N - 1)) == 0 && N != 1) {
            System.out.println("Yes");
            buildTree(N);
        } else if (N == 1) {
            System.out.println("No");
        } else {
            System.out.println("No");
        }
    }

    private static void buildTree(int N) {
        int K = Integer.highestOneBit(N);
        if (K == N) K *= 2;
        
        for (int i = 2; i <= N; i++) {
            System.out.println("1 " + i);
            System.out.println(i + " " + (N + i - 1));
        }
        System.out.println((N + 1) + " " + (2 * N));
        
        if (K != N) {
            int m = N - K + 1;
            System.out.println(m + " " + (N + 1));
        }
    }
}