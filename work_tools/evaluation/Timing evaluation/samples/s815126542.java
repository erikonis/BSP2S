import java.util.*;

public class Main {
    static int N;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        int[] a = new int[N];
        for (int i = 0; i < N; i++) {
            a[i] = sc.nextInt();
        }
        Arrays.sort(a);
        int small = 0;
        int large = N-1;
        long ans = 0;
        int left = a[large];
        int right = a[large];
        large--;
        while (large >= small) {
            int nextSmall = a[small];
            int nextLarge = a[large];
            int nextSmallLeft = Math.abs(left - nextSmall);
            int nextSmallRight = Math.abs(right - nextSmall);
            int nextLargeLeft = Math.abs(left - nextLarge);
            int nextLargeRight = Math.abs(right - nextLarge);
            int s = Math.max(nextSmallLeft, nextSmallRight);
            int l = Math.max(nextLargeLeft, nextLargeRight);
            boolean ll = nextLargeLeft > nextLargeRight;
            boolean sl = nextSmallLeft > nextSmallRight;
            boolean lgt = l > s;
            if(ll && lgt) {
                ans += nextLargeLeft;
                left = a[large--];
            } else if (!ll && lgt) {
                ans += nextLargeRight;
                right = a[large--];
            } else if (sl && !lgt) {
                ans += nextSmallLeft;
                left = a[small++];
            } else if (!sl && !lgt) {
                ans += nextSmallRight;
                right = a[small++];
            }
        }
        System.out.println(ans);
   }
}