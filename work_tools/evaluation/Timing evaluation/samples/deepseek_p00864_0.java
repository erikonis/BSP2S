import java.util.*;

public class GreyArea {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int n = scanner.nextInt();
            int w = scanner.nextInt();
            if (n == 0 && w == 0) break;

            int[] values = new int[n];
            int maxVal = 0;
            for (int i = 0; i < n; i++) {
                values[i] = scanner.nextInt();
                if (values[i] > maxVal) {
                    maxVal = values[i];
                }
            }

            int k = (int) Math.ceil((double) maxVal / w);
            int[] counts = new int[k];
            for (int v : values) {
                int index = v / w;
                counts[index]++;
            }

            int maxCount = 0;
            for (int count : counts) {
                if (count > maxCount) {
                    maxCount = count;
                }
            }

            double ink = 0.01;  // Base ink for drawing lines and characters
            for (int i = 0; i < k; i++) {
                double darkness = (double) (k - 1 - i) / (k - 1);
                double heightRatio = (double) counts[i] / maxCount;
                ink += darkness * heightRatio;
            }

            System.out.println(ink);
        }
    }
}