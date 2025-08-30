import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        
        double sumOfInverses = 0.0;
        for (int i = 0; i < n; i++) {
            int a = sc.nextInt();
            sumOfInverses += 1.0 / a;
        }
        
        double result = 1.0 / sumOfInverses;
        System.out.println(result);
        
        sc.close();
    }
}