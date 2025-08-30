import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class TrafficAnalysis {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            if (n == 0 && m == 0) break;

            ArrayList<Integer> times = new ArrayList<>();

            // Read up line times
            for (int i = 0; i < n; i++) {
                times.add(scanner.nextInt());
            }

            // Read down line times
            for (int i = 0; i < m; i++) {
                times.add(scanner.nextInt());
            }

            // Sort all times
            Collections.sort(times);

            int maxGap = 0;
            int prevTime = 0;
            for (int time : times) {
                int gap = time - prevTime;
                if (gap > maxGap) {
                    maxGap = gap;
                }
                prevTime = time;
            }

            System.out.println(maxGap);
        }
        scanner.close();
    }
}