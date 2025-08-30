import java.awt.Point;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int n = sc.nextInt();
      if (n == 0) {
        break;
      }

      Point[] points = new Point[n];
      Set<Point> pointSet = new HashSet<>();
      for (int i = 0; i < n; i++) {
        int x = sc.nextInt();
        int y = sc.nextInt();
        points[i] = new Point(x, y);
        pointSet.add(points[i]);
      }

      long maxArea = 0;

      for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
          Point p1 = points[i];
          Point p2 = points[j];

          // Vector from p1 to p2
          int dx = p2.x - p1.x;
          int dy = p2.y - p1.y;

          // Candidate for p3 (p1 + rotated vector)
          Point p3 = new Point(p1.x - dy, p1.y + dx);
          // Candidate for p4 (p2 + rotated vector)
          Point p4 = new Point(p2.x - dy, p2.y + dx);

          if (pointSet.contains(p3) && pointSet.contains(p4)) {
            long currentArea = (long) dx * dx + (long) dy * dy;
            if (currentArea > maxArea) {
              maxArea = currentArea;
            }
          }

          // Another possibility for p3 (p1 - rotated vector)
          p3 = new Point(p1.x + dy, p1.y - dx);
          // Another possibility for p4 (p2 - rotated vector)
          p4 = new Point(p2.x + dy, p2.y - dx);

          if (pointSet.contains(p3) && pointSet.contains(p4)) {
            long currentArea = (long) dx * dx + (long) dy * dy;
            if (currentArea > maxArea) {
              maxArea = currentArea;
            }
          }
        }
      }
      System.out.println(maxArea);
    }
    sc.close();
  }
}