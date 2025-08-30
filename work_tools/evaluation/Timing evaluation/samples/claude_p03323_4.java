import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int A = sc.nextInt();
        int B = sc.nextInt();
        
        // Maximum non-adjacent pieces each person can take is 8
        if (A <= 8 && B <= 8) {
            System.out.println("Yay!");
        } else {
            System.out.println(":(");
        }
        
        sc.close();
    }
}