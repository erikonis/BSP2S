import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        long[] A = new long[N];
        long[] B = new long[N];
        long sum = 0;
        
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            A[i] = Long.parseLong(st.nextToken());
            B[i] = Long.parseLong(st.nextToken());
        }
        
        long res = 0;
        boolean allEqual = true;
        for (int i = 0; i < N; i++) {
            if (A[i] != B[i]) {
                allEqual = false;
            }
        }
        if (allEqual) {
            System.out.println(0);
            return;
        }
        
        for (int i = 0; i < N; i++) {
            if (A[i] > B[i]) {
                res += (A[i] + B[i]) - B[i];
            }
        }
        res -= 1;
        System.out.println(res);
    }
}