import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] nums = new int[3];
        for (int i = 0; i < 3; i++) {
            nums[i] = sc.nextInt();
        }
        int K = sc.nextInt();
        
        for (int i = 0; i < K; i++) {
            Arrays.sort(nums);
            nums[2] *= 2;
        }
        
        int sum = nums[0] + nums[1] + nums[2];
        System.out.println(sum);
    }
}