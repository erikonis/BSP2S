import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int n = scanner.nextInt();
            if (n == 0) break;
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = scanner.nextInt();
            }
            int k = scanner.nextInt();
            
            int left = 0;
            int right = n - 1;
            int count = 0;
            
            while (left <= right) {
                count++;
                int mid = (left + right) / 2;
                if (arr[mid] == k) {
                    break;
                } else if (arr[mid] < k) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            System.out.println(count);
        }
    }
}