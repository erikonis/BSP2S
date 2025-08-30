import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long A = sc.nextLong();
        long B = sc.nextLong();
        long N = sc.nextLong();
        
        long result;
        if (N >= B - 1) {
            result = A - 1;
        } else {
            result = (A * N) / B;
        }
        
        System.out.println(result);
    }
}