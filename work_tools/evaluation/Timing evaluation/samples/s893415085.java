
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int h = Integer.parseInt(scan.next());
        int w = Integer.parseInt(scan.next());
        int[][] c = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                c[i][j] = Integer.parseInt(scan.next());
            }
        }
        int[] a = new int[10];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int b = Integer.parseInt(scan.next());
                if (b != -1) {
                    a[b]++;
                }
            }
        }
        scan.close();

        for (int k = 0; k < 10; k++) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    c[i][j] = Math.min(c[i][j], c[i][k] + c[k][j]);
                }
            }
        }

        int ans = 0;
        for (int i = 0; i < a.length; i++) {
            ans += c[i][1] * a[i];
        }
        System.out.println(ans);
    }
}
