import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        Integer[] A = new Integer[N];
        Integer[] B = new Integer[M];
        for (int i=0;i<N;i++) A[i] = sc.nextInt();
        for (int i=0;i<M;i++) B[i] = sc.nextInt();
        Arrays.sort(A);
        Arrays.sort(B);

        long ans = 1L;

        for (int i=0;i<N-1;i++) {
            if (A[i]==A[i+1]) ans = 0L;
        }
        for (int i=0;i<M-1;i++) {
            if (B[i]==B[i+1]) ans = 0L;
        }
        int mod = 1_000_000_007;
        for (int i=N*M;i>0;i--) {
            int pos_A = Arrays.binarySearch(A, i);
            int pos_B = Arrays.binarySearch(B, i);
            if (pos_A>=0 && pos_B>=0) {
                continue;
            } else if (pos_A>=0 && pos_B<0) {
                int tmp = ~Arrays.binarySearch(B, i, (o1, o2)->o1>=o2?1:-1);
                ans = ans*(M-tmp)%mod;
            } else if (pos_B>=0 && pos_A<0) {
                int tmp = ~Arrays.binarySearch(A, i, (o1, o2)->o1>=o2?1:-1);
                ans = ans*(N-tmp)%mod;
            } else { // pos_A<0 && pos_B<0
                int tmp_1 = ~Arrays.binarySearch(A, i, (o1, o2)->o1>=o2?1:-1);
                int tmp_2 = ~Arrays.binarySearch(B, i, (o1, o2)->o1>=o2?1:-1);
                ans = ans*Math.max(0, (N-tmp_1)*(M-tmp_2)-(N*M-i))%mod;
            }
        }
        System.out.println(ans);
    }
}