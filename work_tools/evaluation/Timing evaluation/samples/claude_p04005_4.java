import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long A = sc.nextLong();
        long B = sc.nextLong();
        long C = sc.nextLong();
        
        long minDiff = Long.MAX_VALUE;
        
        // Split along A dimension
        long mid = A / 2;
        long vol1 = mid * B * C;
        long vol2 = (A - mid) * B * C;
        minDiff = Math.min(minDiff, Math.abs(vol1 - vol2));
        
        // Split along B dimension
        mid = B / 2;
        vol1 = A * mid * C;
        vol2 = A * (B - mid) * C;
        minDiff = Math.min(minDiff, Math.abs(vol1 - vol2));
        
        // Split along C dimension
        mid = C / 2;
        vol1 = A * B * mid;
        vol2 = A * B * (C - mid);
        minDiff = Math.min(minDiff, Math.abs(vol1 - vol2));
        
        System.out.println(minDiff);
        sc.close();
    }
}