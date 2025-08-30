import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        double x1 = sc.nextDouble();
        double y1 = sc.nextDouble();
        double x2 = sc.nextDouble();
        double y2 = sc.nextDouble();
        
        int q = sc.nextInt();
        
        for (int i = 0; i < q; i++) {
            double px = sc.nextDouble();
            double py = sc.nextDouble();
            
            // Vector from p1 to p2
            double dx = x2 - x1;
            double dy = y2 - y1;
            
            // Vector from p1 to p
            double dpx = px - x1;
            double dpy = py - y1;
            
            // Project vector (p1 to p) onto vector (p1 to p2)
            double dotProduct = dpx * dx + dpy * dy;
            double lengthSquared = dx * dx + dy * dy;
            double t = dotProduct / lengthSquared;
            
            // Calculate projection point
            double projX = x1 + t * dx;
            double projY = y1 + t * dy;
            
            System.out.printf("%.10f %.10f\n", projX, projY);
        }
        
        sc.close();
    }
}