import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int R = sc.nextInt();
        int C = sc.nextInt();
        int N = sc.nextInt();
        
        List<Segment> segments = new ArrayList<>();
        
        for (int i = 0; i < N; i++) {
            int x1 = sc.nextInt(), y1 = sc.nextInt();
            int x2 = sc.nextInt(), y2 = sc.nextInt();
            
            long pos1 = getBoundaryPosition(x1, y1, R, C);
            long pos2 = getBoundaryPosition(x2, y2, R, C);
            
            segments.add(new Segment(Math.min(pos1, pos2), Math.max(pos1, pos2)));
        }
        
        // Check if any two segments cross
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (cross(segments.get(i), segments.get(j))) {
                    System.out.println("NO");
                    return;
                }
            }
        }
        
        System.out.println("YES");
    }
    
    // Map point to position on boundary (clockwise from top-left)
    static long getBoundaryPosition(int x, int y, int R, int C) {
        if (x == 0) return y; // Top edge
        if (y == C) return C + x; // Right edge  
        if (x == R) return C + R + (C - y); // Bottom edge
        if (y == 0) return C + R + C + (R - x); // Left edge
        
        // Point is inside, find closest boundary
        int distTop = x, distRight = C - y, distBottom = R - x, distLeft = y;
        int minDist = Math.min(Math.min(distTop, distRight), Math.min(distBottom, distLeft));
        
        if (minDist == distTop) return y;
        if (minDist == distRight) return C + x;
        if (minDist == distBottom) return C + R + (C - y);
        return C + R + C + (R - x);
    }
    
    // Check if two segments cross
    static boolean cross(Segment a, Segment b) {
        return (a.start < b.start && b.start < a.end && a.end < b.end) ||
               (b.start < a.start && a.start < b.end && b.end < a.end);
    }
    
    static class Segment {
        long start, end;
        Segment(long start, long end) {
            this.start = start;
            this.end = end;
        }
    }
}