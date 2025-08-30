import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Fast input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int L = Integer.parseInt(br.readLine());
        long[] A = new long[L];
        for (int i = 0; i < L; i++) {
            A[i] = Long.parseLong(br.readLine());
        }

        // The best Snuke can do is: for every pair (A[i], A[i+1]), make the number of stones left in ear i and i+1 
        // as close as possible, since a walk can increase/decrease both by 1 in one pass.
        // The minimum number of operations needed is (sum of abs difference between consecutive Ai's))/2
        // And finally, possibly we need to add abs(A[0]) and abs(A[L-1])/2 for the ends

        long ops = 0;
        // The initial virtual "ear" at left is always 0
        long prev = 0;
        for (int i = 0; i < L; i++) {
            ops += Math.abs(A[i] - prev);
            prev = A[i];
        }
        System.out.println(ops / 2);
    }
}