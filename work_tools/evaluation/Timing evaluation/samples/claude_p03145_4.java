import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();
        int c = sc.nextInt();
        
        // Using Heron's formula: Area = sqrt(s(s-a)(s-b)(s-c))
        // where s = (a+b+c)/2
        double s = (a + b + c) / 2.0;
        double area = Math.sqrt(s * (s - a) * (s - b) * (s - c));
        
        // Since area is guaranteed to be integer, we can safely cast
        System.out.println((int)Math.round(area));
    }
}