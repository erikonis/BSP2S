import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int x1 = sc.nextInt();
        int y1 = sc.nextInt();
        int x2 = sc.nextInt();
        int y2 = sc.nextInt();
        
        // Vector from vertex 1 to vertex 2
        int dx = x2 - x1;
        int dy = y2 - y1;
        
        // Rotate 90° counter-clockwise: (dx, dy) -> (-dy, dx)
        int rotDx = -dy;
        int rotDy = dx;
        
        // Vertex 3: move from vertex 2 in rotated direction
        int x3 = x2 + rotDx;
        int y3 = y2 + rotDy;
        
        // Vertex 4: move from vertex 1 in rotated direction
        int x4 = x1 + rotDx;
        int y4 = y1 + rotDy;
        
        System.out.println(x3 + " " + y3 + " " + x4 + " " + y4);
    }
}