import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int A = sc.nextInt();
        int B = sc.nextInt();
        int C = sc.nextInt();
        int K = sc.nextInt();
        
        // Store the three numbers in an array for easier manipulation
        int[] nums = {A, B, C};
        
        // Perform K operations
        for (int i = 0; i < K; i++) {
            // Find the index of the maximum number
            int maxIndex = 0;
            for (int j = 1; j < 3; j++) {
                if (nums[j] > nums[maxIndex]) {
                    maxIndex = j;
                }
            }
            // Double the maximum number
            nums[maxIndex] *= 2;
        }
        
        // Calculate and print the sum
        int sum = nums[0] + nums[1] + nums[2];
        System.out.println(sum);
        
        sc.close();
    }
}