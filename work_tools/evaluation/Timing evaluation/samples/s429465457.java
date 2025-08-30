import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[][] points = new int[N][2];
        for(int i = 0; i < N; i++) {
            points[i][0] = sc.nextInt();
            points[i][1] = sc.nextInt();
        }

        Arrays.sort(points, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                long s1 = o1[0] + o1[1];
                long s2 = o2[0] + o2[1];
                return -1 * Long.compare(s1, s2);
            }
        });

        long ans = 0;
        for(int i = 0; i < N; i++) {
            if (i % 2 == 0) {
                ans += points[i][0];
            } else {
                ans -= points[i][1];
            }
        }

        System.out.println(ans);
    }
}
