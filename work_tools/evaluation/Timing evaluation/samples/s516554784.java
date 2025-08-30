import java.util.Arrays;
import java.util.Scanner;

/**
 * Engines
 */
public class Main {

    public static void main(String[] args) throws Exception {
        try (Scanner sc = new Scanner(System.in)) {
            int N = sc.nextInt();
            Vector[] V = new Vector[N];
            for (int i = 0; i < N; i++) {
                V[i] = new Vector();
                V[i].x = sc.nextLong();
                V[i].y = sc.nextLong();
            }

            for (Vector v : V) {
                v.r = Math.acos(v.x / Math.sqrt(v.x * v.x + v.y * v.y));
                if (v.y < 0) {
                    v.r = Math.PI * 2 - v.r;
                }
            }

            Arrays.sort(V, (o1, o2) -> Double.compare(o1.r, o2.r));

            double max = 0;

            for (int i = 0; i < N; i++) {
                long X = 0;
                long Y = 0;

                for (int j = i; j < N + i; j++) {
                    X += V[j % N].x;
                    Y += V[j % N].y;

                    double d = Math.sqrt(X * X + Y * Y);

                    if (max < d) {
                        max = d;
                    }
                }
            }

            System.out.println(max);
        }

    }

    static class Vector {

        long x;

        long y;

        double r;

    }

}
