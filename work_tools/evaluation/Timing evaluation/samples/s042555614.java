import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Harry on 5/10/20.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        int n = scanner.nextInt();
        long[] nums = new long[n];
        long[] sum = new long[n+1];
        int mod = (int)(1e9+7);
        for(int i=0; i<n; i++){
            nums[i] = scanner.nextLong();
            sum[i+1] = sum[i]+nums[i];
        }
        long res = 0;
        for(int i=0; i<n; i++){
            res = (res + nums[i]*((sum[n]-sum[i+1]+mod)%mod)%mod )%mod;
        }
        System.out.println(res%mod);
    }
}
