import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        
        // Calculate sum of digits
        int sum = 0;
        int temp = N;
        while (temp > 0) {
            sum += temp % 10;
            temp /= 10;
        }
        
        // Check if N is divisible by sum of digits
        if (N % sum == 0) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
        
        sc.close();
    }
}