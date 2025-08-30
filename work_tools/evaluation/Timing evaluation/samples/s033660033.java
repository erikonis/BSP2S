import java.io.*;
import java.util.StringTokenizer;

import static java.lang.Integer.*;
import static java.lang.Long.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter prnt = new PrintWriter(System.out);

        int n = parseInt(read.readLine());
        long[] arr = new long[n];
        StringTokenizer st = new StringTokenizer(read.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = parseLong(st.nextToken());
        }
        prnt.println(getMinimumTotalHeight(arr, n));
        prnt.close();
    }

    static long getMinimumTotalHeight(long[] arr, int n) {
        long heightOfstool = 0;
        for (int i = 1; i < n; i++) {
            if (arr[i - 1] > arr[i]) {
                heightOfstool += (arr[i - 1] - arr[i]);
                arr[i] += (arr[i - 1] - arr[i]);
            }
        }
        return heightOfstool;
    }
}