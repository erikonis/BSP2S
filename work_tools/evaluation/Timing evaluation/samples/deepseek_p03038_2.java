import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] nm = br.readLine().split(" ");
        int N = Integer.parseInt(nm[0]);
        int M = Integer.parseInt(nm[1]);

        String[] aStr = br.readLine().split(" ");
        long[] A = new long[N];
        for (int i = 0; i < N; i++) {
            A[i] = Long.parseLong(aStr[i]);
        }

        PriorityQueue<Long> minHeap = new PriorityQueue<>();
        for (long num : A) {
            minHeap.add(num);
        }

        PriorityQueue<Pair> maxHeap = new PriorityQueue<>((a, b) -> Long.compare(b.c, a.c));
        for (int i = 0; i < M; i++) {
            String[] bc = br.readLine().split(" ");
            int b = Integer.parseInt(bc[0]);
            long c = Long.parseLong(bc[1]);
            maxHeap.add(new Pair(b, c));
        }

        while (!maxHeap.isEmpty()) {
            Pair current = maxHeap.poll();
            int b = current.b;
            long c = current.c;
            for (int i = 0; i < b; i++) {
                if (!minHeap.isEmpty() && minHeap.peek() < c) {
                    minHeap.poll();
                    minHeap.add(c);
                } else {
                    break;
                }
            }
        }

        long sum = 0;
        while (!minHeap.isEmpty()) {
            sum += minHeap.poll();
        }
        System.out.println(sum);
    }

    static class Pair {
        int b;
        long c;
        Pair(int b, long c) {
            this.b = b;
            this.c = c;
        }
    }
}