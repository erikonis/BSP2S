import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] a = new int[N];
        int[] b = new int[N];
        for (int i = 0; i < N; i++) a[i] = sc.nextInt();
        for (int i = 0; i < N; i++) b[i] = sc.nextInt();
        
        int result = 0;
        
        for (int k = 0; k <= 28; k++) {
            int mask = (1 << (k + 1)) - 1;
            int[] aMod = new int[N];
            int[] bMod = new int[N];
            for (int i = 0; i < N; i++) {
                aMod[i] = a[i] & mask;
                bMod[i] = b[i] & mask;
            }
            Arrays.sort(bMod);
            
            long count = 0;
            int T = 1 << k;
            for (int ai : aMod) {
                int low1 = T - ai;
                int high1 = 2 * T - ai;
                int cnt1 = countInRange(bMod, low1, high1 - 1);
                
                int low2 = 3 * T - ai;
                int high2 = 4 * T - ai;
                int cnt2 = countInRange(bMod, low2, high2 - 1);
                
                count += cnt1 + cnt2;
            }
            if ((count & 1) == 1) {
                result |= 1 << k;
            }
        }
        System.out.println(result);
    }
    
    static int countInRange(int[] arr, int low, int high) {
        int left = lowerBound(arr, low);
        int right = lowerBound(arr, high + 1);
        return right - left;
    }
    
    static int lowerBound(int[] arr, int target) {
        int left = 0, right = arr.length;
        while (left < right) {
            int mid = (left + right) >>> 1;
            if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
}