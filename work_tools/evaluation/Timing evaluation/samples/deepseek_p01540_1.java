import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        int[][] treasures = new int[n][2];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            treasures[i][0] = Integer.parseInt(st.nextToken());
            treasures[i][1] = Integer.parseInt(st.nextToken());
        }
        
        Arrays.sort(treasures, (a, b) -> {
            if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
            return Integer.compare(a[1], b[1]);
        });
        
        int[] xCoords = new int[n];
        int[] yCoords = new int[n];
        for (int i = 0; i < n; i++) {
            xCoords[i] = treasures[i][0];
            yCoords[i] = treasures[i][1];
        }
        
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            int y2 = Integer.parseInt(st.nextToken());
            
            int left = lowerBound(xCoords, x1);
            int right = upperBound(xCoords, x2);
            
            int count = 0;
            for (int j = left; j < right; j++) {
                if (yCoords[j] >= y1 && yCoords[j] <= y2) {
                    count++;
                }
            }
            pw.println(count);
        }
        pw.flush();
    }
    
    private static int lowerBound(int[] arr, int target) {
        int left = 0;
        int right = arr.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
    
    private static int upperBound(int[] arr, int target) {
        int left = 0;
        int right = arr.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
}