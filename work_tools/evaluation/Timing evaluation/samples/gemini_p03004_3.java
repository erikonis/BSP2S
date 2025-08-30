import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        long[] x = new long[N];
        long[] y = new long[N];
        char[] d = new char[N];
        for (int i = 0; i < N; i++) {
            x[i] = sc.nextLong();
            y[i] = sc.nextLong();
            d[i] = sc.next().charAt(0);
        }

        List<Double> times = new ArrayList<>();
        times.add(0.0);

        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                // Check if x-range can become 0
                if (d[i] == 'R' && d[j] == 'L') {
                    if (x[i] < x[j]) {
                        times.add((double) (x[j] - x[i]) / 2.0);
                    }
                } else if (d[i] == 'L' && d[j] == 'R') {
                    if (x[j] < x[i]) {
                        times.add((double) (x[i] - x[j]) / 2.0);
                    }
                } else if (d[i] == 'R' && d[j] == 'R' && x[i] > x[j]) {
                    times.add((double) (x[i] - x[j]));
                } else if (d[i] == 'L' && d[j] == 'L' && x[i] < x[j]) {
                    times.add((double) (x[j] - x[i]));
                } else if (d[i] == 'U' && d[j] == 'U' && y[i] > y[j]) {
                    times.add((double) (y[i] - y[j]));
                } else if (d[i] == 'D' && d[j] == 'D' && y[i] < y[j]) {
                    times.add((double) (y[j] - y[i]));
                }


                // Check if y-range can become 0
                if (d[i] == 'U' && d[j] == 'D') {
                    if (y[i] < y[j]) {
                        times.add((double) (y[j] - y[i]) / 2.0);
                    }
                } else if (d[i] == 'D' && d[j] == 'U') {
                    if (y[j] < y[i]) {
                        times.add((double) (y[i] - y[j]) / 2.0);
                    }
                }
            }
        }

        double minArea = Double.MAX_VALUE;

        for (double t : times) {
            double currentMinX = Double.MAX_VALUE;
            double currentMaxX = Double.MIN_VALUE;
            double currentMinY = Double.MAX_VALUE;
            double currentMaxY = Double.MIN_VALUE;

            for (int i = 0; i < N; i++) {
                double currentX = x[i];
                double currentY = y[i];

                if (d[i] == 'R') {
                    currentX += t;
                } else if (d[i] == 'L') {
                    currentX -= t;
                } else if (d[i] == 'U') {
                    currentY += t;
                } else if (d[i] == 'D') {
                    currentY -= t;
                }

                currentMinX = Math.min(currentMinX, currentX);
                currentMaxX = Math.max(currentMaxX, currentX);
                currentMinY = Math.min(currentMinY, currentY);
                currentMaxY = Math.max(currentMaxY, currentY);
            }

            double area = (currentMaxX - currentMinX) * (currentMaxY - currentMinY);
            minArea = Math.min(minArea, area);
        }

        System.out.println(minArea);
    }
}