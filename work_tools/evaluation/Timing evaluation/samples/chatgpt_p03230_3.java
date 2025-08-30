import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        // The only possible N is 3
        if (N != 3) {
            System.out.println("No");
            return;
        }

        // For N = 3:
        // S1 = {1,2}
        // S2 = {2,3}
        // S3 = {3,1}
        System.out.println("Yes");
        System.out.println("3");
        System.out.println("2 1 2");
        System.out.println("2 2 3");
        System.out.println("2 3 1");
    }
}