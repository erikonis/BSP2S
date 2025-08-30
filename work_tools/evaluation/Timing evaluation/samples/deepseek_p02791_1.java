import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        String[] parts = br.readLine().split(" ");
        int[] P = new int[N];
        for (int i = 0; i < N; i++) {
            P[i] = Integer.parseInt(parts[i]);
        }
        
        int count = 0;
        int minSoFar = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            if (P[i] < minSoFar) {
                minSoFar = P[i];
                count++;
            }
        }
        System.out.println(count);
    }
}