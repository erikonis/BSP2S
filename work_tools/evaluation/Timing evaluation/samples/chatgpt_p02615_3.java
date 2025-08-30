import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        long[] A = new long[N];
        for(int i=0;i<N;i++) A[i] = sc.nextLong();
        Arrays.sort(A);
        long res = A[N-1];
        int l = N-2, r = N-2;
        for(int i=0;i<N-2;i++){
            if(i%2==0){
                res += A[l--];
            }else{
                res += A[r--];
            }
        }
        System.out.println(res);
    }
}