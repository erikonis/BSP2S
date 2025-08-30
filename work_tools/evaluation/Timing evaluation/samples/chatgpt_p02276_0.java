import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] A = new int[n];
        for(int i=0;i<n;i++) A[i] = sc.nextInt();
        int pivotIdx = partition(A, 0, n-1);
        for(int i=0;i<n;i++) {
            if(i == pivotIdx) {
                System.out.print("[" + A[i] + "]");
            } else {
                System.out.print(A[i]);
            }
            if(i < n-1) System.out.print(" ");
        }
    }
    
    // Lomuto's partition, alternative with extra arrays
    static int partition(int[] A, int p, int r) {
        int x = A[r];
        int[] left = new int[A.length];
        int[] right = new int[A.length];
        int l=0, ri=0;
        for(int j=p;j<r;j++) {
            if(A[j]<=x) left[l++] = A[j];
            else right[ri++] = A[j];
        }
        int idx = p;
        for(int i=0;i<l;i++) A[idx++] = left[i];
        int pivotPos = idx;
        A[idx++] = x;
        for(int i=0;i<ri;i++) A[idx++] = right[i];
        return pivotPos;
    }
}