import java.util.*;

public class Main {
    static class Point {
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    static class PolygonalLine {
        List<Point> points;
        
        PolygonalLine(List<Point> points) {
            this.points = new ArrayList<>(points);
        }
        
        // Get direction vectors between consecutive points
        List<Point> getDirections() {
            List<Point> dirs = new ArrayList<>();
            for (int i = 0; i < points.size() - 1; i++) {
                Point p1 = points.get(i);
                Point p2 = points.get(i + 1);
                dirs.add(new Point(p2.x - p1.x, p2.y - p1.y));
            }
            return dirs;
        }
        
        // Rotate direction 90 degrees counterclockwise
        Point rotate90(Point dir) {
            return new Point(-dir.y, dir.x);
        }
        
        // Check if two direction sequences match (considering rotations)
        boolean matchesDirections(List<Point> template, List<Point> candidate) {
            if (template.size() != candidate.size()) return false;
            
            for (int rotation = 0; rotation < 4; rotation++) {
                boolean matches = true;
                for (int i = 0; i < template.size(); i++) {
                    Point tDir = template.get(i);
                    Point cDir = candidate.get(i);
                    
                    // Apply rotation
                    for (int r = 0; r < rotation; r++) {
                        tDir = rotate90(tDir);
                    }
                    
                    if (tDir.x != cDir.x || tDir.y != cDir.y) {
                        matches = false;
                        break;
                    }
                }
                if (matches) return true;
            }
            return false;
        }
        
        // Check if this line has same shape as template
        boolean hasSameShape(PolygonalLine template) {
            List<Point> templateDirs = template.getDirections();
            List<Point> thisDirs = this.getDirections();
            
            // Check forward direction
            if (matchesDirections(templateDirs, thisDirs)) return true;
            
            // Check reverse direction
            List<Point> reverseDirs = new ArrayList<>();
            for (int i = thisDirs.size() - 1; i >= 0; i--) {
                Point dir = thisDirs.get(i);
                reverseDirs.add(new Point(-dir.x, -dir.y));
            }
            
            return matchesDirections(templateDirs, reverseDirs);
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            int n = sc.nextInt();
            if (n == 0) break;
            
            List<PolygonalLine> lines = new ArrayList<>();
            
            // Read all polygonal lines
            for (int i = 0; i <= n; i++) {
                int m = sc.nextInt();
                List<Point> points = new ArrayList<>();
                for (int j = 0; j < m; j++) {
                    int x = sc.nextInt();
                    int y = sc.nextInt();
                    points.add(new Point(x, y));
                }
                lines.add(new PolygonalLine(points));
            }
            
            PolygonalLine template = lines.get(0);
            List<Integer> matches = new ArrayList<>();
            
            // Check each line against template
            for (int i = 1; i <= n; i++) {
                if (lines.get(i).hasSameShape(template)) {
                    matches.add(i);
                }
            }
            
            // Output results
            for (int match : matches) {
                System.out.println(match);
            }
            System.out.println("+++++");
        }
    }
}