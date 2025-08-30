import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (scanner.hasNext()) {
            double xa1 = scanner.nextDouble();
            double ya1 = scanner.nextDouble();
            double xa2 = scanner.nextDouble();
            double ya2 = scanner.nextDouble();
            double xb1 = scanner.nextDouble();
            double yb1 = scanner.nextDouble();
            double xb2 = scanner.nextDouble();
            double yb2 = scanner.nextDouble();
            
            // Check if rectangles overlap
            boolean xOverlap = xa1 <= xb2 && xb1 <= xa2;
            boolean yOverlap = ya1 <= yb2 && yb1 <= ya2;
            
            if (xOverlap && yOverlap) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
        
        scanner.close();
    }
}