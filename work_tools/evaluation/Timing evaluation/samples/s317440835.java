import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        PriorityQueue<Integer> q = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i = 0; i < n; i++) {
            q.add(sc.nextInt());
        }
        for (int i = 0; i < m; i++) {
            int x = q.poll();
            x = x/2;
            q.add(x);
        }
        long ans = 0L;
        for (Integer x : q) {
            ans += (long)x;
        }
        System.out.println(ans);
        sc.close();

    }

}
