import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int N = sc.nextInt();
            int M = sc.nextInt();

            if (N == 0 && M == 0) {
                break;
            }

            int[] h = new int[N];
            for (int i = 0; i < N; i++) {
                h[i] = sc.nextInt();
            }

            int[] w = new int[M];
            for (int i = 0; i < M; i++) {
                w[i] = sc.nextInt();
            }

            Map<Integer, Integer> hLengthCounts = new HashMap<>();
            for (int i = 0; i < N; i++) {
                int currentLength = 0;
                for (int j = i; j < N; j++) {
                    currentLength += h[j];
                    hLengthCounts.put(currentLength, hLengthCounts.getOrDefault(currentLength, 0) + 1);
                }
            }

            Map<Integer, Integer> wLengthCounts = new HashMap<>();
            for (int i = 0; i < M; i++) {
                int currentLength = 0;
                for (int j = i; j < M; j++) {
                    currentLength += w[j];
                    wLengthCounts.put(currentLength, wLengthCounts.getOrDefault(currentLength, 0) + 1);
                }
            }

            long squareCount = 0;
            for (Map.Entry<Integer, Integer> entry : hLengthCounts.entrySet()) {
                int length = entry.getKey();
                int hCount = entry.getValue();
                if (wLengthCounts.containsKey(length)) {
                    squareCount += (long) hCount * wLengthCounts.get(length);
                }
            }
            System.out.println(squareCount);
        }
        sc.close();
    }
}