import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int A = sc.nextInt();
        int B = sc.nextInt();
        int C = sc.nextInt();
        int D = sc.nextInt();
        
        int area1 = A * B;
        int area2 = C * D;
        
        System.out.println(Math.max(area1, area2));
        
        sc.close();
    }
}