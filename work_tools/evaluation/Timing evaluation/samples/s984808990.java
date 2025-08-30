import java.util.Scanner;
public class Main {
    public static void main(String[] defargs) {
        Scanner scanner = new Scanner(System.in);
        double r = scanner.nextDouble();
        double pi = Math.PI;
        double area = r * r * pi;
        double ensyu = (double)2 *(double) pi * r;
        System.out.printf("%f %f\n" , area , ensyu);
    }
}
