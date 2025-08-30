import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long N = sc.nextLong();
        int K = sc.nextInt();
        long ans = 0;
        for (int b=K+1;b<=N;b++) {
            ans = ans + N/b*Math.max((b-K), 0) + (long)Math.max(N-N/b*b-K+1, 0);
        }
        if (K==0) {
            ans = N*N;
        }
        System.out.println(ans);
    }
}