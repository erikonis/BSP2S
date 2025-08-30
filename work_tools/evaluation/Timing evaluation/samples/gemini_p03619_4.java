import java.util.*;

public class Main {
    private static final double FOUNTAIN_RADIUS = 10.0;
    private static final double STREET_AVENUE_DISTANCE = 100.0;
    private static final double FOUNTAIN_CIRCUMFERENCE_HALF = Math.PI * FOUNTAIN_RADIUS;

    static class Point {
        long x, y;

        Point(long x, long y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        long sx = sc.nextLong();
        long sy = sc.nextLong();
        long tx = sc.nextLong();
        long ty = sc.nextLong();

        int N = sc.nextInt();
        Point[] fountains = new Point[N];
        for (int i = 0; i < N; i++) {
            fountains[i] = new Point(sc.nextLong(), sc.nextLong());
        }

        // Create a list of all relevant points: start, end, and fountains
        List<Point> allPoints = new ArrayList<>();
        allPoints.add(new Point(sx, sy)); // Start point (index 0)
        allPoints.add(new Point(tx, ty)); // End point (index 1)
        for (Point f : fountains) {
            allPoints.add(f);
        }

        int numNodes = allPoints.size();
        double[][] dist = new double[numNodes][numNodes];

        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                    continue;
                }

                Point p1 = allPoints.get(i);
                Point p2 = allPoints.get(j);

                // Manhattan distance as the base
                dist[i][j] = (Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y)) * STREET_AVENUE_DISTANCE;

                // If both points are fountains, consider the path through their perimeters
                if (i >= 2 && j >= 2) { // Both are fountain points
                    long dx = Math.abs(p1.x - p2.x);
                    long dy = Math.abs(p1.y - p2.y);

                    // If fountains are on the same street or avenue, they are "connected"
                    // and the actual distance is Manhattan minus 2 * 10m (for entering and exiting the circle)
                    // plus 2 * PI * 10m / 4 for the arc.
                    // This simplifies to Manhattan - 20 + PI * 10/2 = Manhattan - 20 + 5 * PI
                    // The problem statement says "every street contains at most one fountain ... as well as every avenue."
                    // This means two fountains cannot share the same x or y coordinate.
                    // Therefore, dx > 0 and dy > 0 for any two distinct fountains.
                    // The alternative path through fountain perimeters is (dx-1 + dy-1) * 100 + 2 * PI * 10 / 2
                    // = (dx+dy)*100 - 200 + PI * 10
                    // This is the distance if we consider moving along the roads, but avoiding the 10m radius.
                    // The actual path is to travel to a point adjacent to the fountain, then around the fountain, then from the other side.
                    // The cost to move from (X1, Y1) to (X2, Y2) using fountains is:
                    // |X1-X2| * 100 + |Y1-Y2| * 100 - 2 * 10 (for each fountain) + PI * 10 (for each fountain arc)
                    // Simplified: ( |X1-X2| + |Y1-Y2| ) * 100 - 2 * FOUNTAIN_RADIUS + FOUNTAIN_CIRCUMFERENCE_HALF
                    // This is for going from one fountain to another. The "straight" path is (dx+dy)*100.
                    // The path through the fountain perimeters is:
                    // (dx - 1) * 100 + (dy - 1) * 100 (horizontal and vertical segments outside the circles)
                    // + 2 * FOUNTAIN_RADIUS * 2 (entering/exiting the circles twice)
                    // + 2 * (PI * FOUNTAIN_RADIUS / 2) (two quarter-circle arcs)
                    // This is not correct. The simplified path is to go to the intersection of the streets/avenues
                    // that would connect the fountains, then around the fountain.
                    // The path between fountain F1(X1,Y1) and F2(X2,Y2) can be:
                    // 1. Straight road: |X1-X2|*100 + |Y1-Y2|*100
                    // 2. Using fountain perimeter:
                    //    If (X1,Y1) and (X2,Y2) are distinct, they are not on the same street/avenue.
                    //    The path is from F1 to (X1,Y2) or (X2,Y1) then to F2.
                    //    The distance from F1(X1,Y1) to F2(X2,Y2) would be:
                    //    |X1-X2| * 100 + |Y1-Y2| * 100.
                    //    However, if we use the perimeter of F1, we travel along X1 avenue to Y2 street,
                    //    and then along Y2 street to X2 avenue, and then along X2 avenue to Y2 street.
                    //    This is equivalent to traveling along X1 to X2 and Y1 to Y2.
                    //    The actual reduction in distance only happens for the 20m segment near each fountain.
                    //    So, the difference is 2 * FOUNTAIN_RADIUS (the distance we "skip") and add FOUNTAIN_CIRCUMFERENCE_HALF.
                    //    So, dist[i][j] = (dx * 100 - FOUNTAIN_RADIUS) + (dy * 100 - FOUNTAIN_RADIUS) + 2 * FOUNTAIN_CIRCUMFERENCE_HALF
                    //    This means: (dx+dy)*100 - 2*FOUNTAIN_RADIUS + 2*FOUNTAIN_CIRCUMFERENCE_HALF
                    //    This is the alternative route through the fountain perimeters for two fountains.
                    double fountainPath = (dx + dy) * STREET_AVENUE_DISTANCE - 2 * FOUNTAIN_RADIUS + 2 * FOUNTAIN_CIRCUMFERENCE_HALF;
                    dist[i][j] = Math.min(dist[i][j], fountainPath);
                }
            }
        }

        // Floyd-Warshall to find all-pairs shortest paths
        for (int k = 0; k < numNodes; k++) {
            for (int i = 0; i < numNodes; i++) {
                for (int j = 0; j < numNodes; j++) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }

        // The shortest distance between start (index 0) and end (index 1)
        System.out.println(dist[0][1]);

        sc.close();
    }
}