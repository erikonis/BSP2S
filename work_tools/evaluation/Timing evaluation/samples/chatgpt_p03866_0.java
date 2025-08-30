import java.util.*;
import java.io.*;

public class Main {
    static class Circle {
        double x, y, r;
        Circle(double x, double y, double r) {
            this.x = x; this.y = y; this.r = r;
        }
    }

    static double EPS = 1e-10;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        double xs = sc.nextInt(), ys = sc.nextInt();
        double xt = sc.nextInt(), yt = sc.nextInt();
        int N = sc.nextInt();
        Circle[] cs = new Circle[N];
        for (int i = 0; i < N; i++) {
            double x = sc.nextInt(), y = sc.nextInt(), r = sc.nextInt();
            cs[i] = new Circle(x, y, r);
        }

        // If the start and end are both inside some circle, and the path can be made entirely inside, answer is 0.
        if (isInsideAny(xs, ys, cs) && isInsideAny(xt, yt, cs) && canConnectWithCircles(xs, ys, xt, yt, cs)) {
            System.out.printf("%.10f\n", 0.0);
            return;
        }

        // Dijkstra's on a graph where nodes are: start, end, and tangent points on circles
        // However, as a strong simplification, since the shortest exposed path is the straight line minus the parts within any circle,
        // we can compute for the straight line, sum up all intervals that are within at least one barrier, and subtract from the total.
        // This gives the minimal possible exposure (since going through circles is allowed).
        double total_dist = dist(xs, ys, xt, yt);
        List<Interval> intervals = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            Interval interval = getInCircleInterval(xs, ys, xt, yt, cs[i].x, cs[i].y, cs[i].r);
            if (interval != null) intervals.add(interval);
        }
        List<Interval> merged = mergeIntervals(intervals);
        double covered = 0.0;
        for (Interval interval : merged) {
            double a = interval.l, b = interval.r;
            covered += (b - a);
        }
        double exposed = total_dist - covered;
        if (exposed < 0) exposed = 0.0;
        System.out.printf("%.10f\n", exposed);
    }

    static boolean isInsideAny(double x, double y, Circle[] cs) {
        for (Circle c : cs) {
            if (Math.hypot(x - c.x, y - c.y) <= c.r + EPS) return true;
        }
        return false;
    }

    // If both points are inside the same circle, or in overlapping circles, path can be inside circles.
    static boolean canConnectWithCircles(double xs, double ys, double xt, double yt, Circle[] cs) {
        int N = cs.length;
        boolean[] startIn = new boolean[N];
        boolean[] endIn = new boolean[N];
        for (int i = 0; i < N; i++) {
            if (Math.hypot(xs - cs[i].x, ys - cs[i].y) <= cs[i].r + EPS) startIn[i] = true;
            if (Math.hypot(xt - cs[i].x, yt - cs[i].y) <= cs[i].r + EPS) endIn[i] = true;
        }
        // BFS to see if any circle containing start is connected (overlapping) to any circle containing end
        boolean[] visited = new boolean[N];
        Queue<Integer> que = new ArrayDeque<>();
        for (int i = 0; i < N; i++) if (startIn[i]) { que.add(i); visited[i] = true; }
        while (!que.isEmpty()) {
            int cur = que.poll();
            if (endIn[cur]) return true;
            for (int i = 0; i < N; i++) if (!visited[i]) {
                double d = Math.hypot(cs[cur].x - cs[i].x, cs[cur].y - cs[i].y);
                if (d <= cs[cur].r + cs[i].r + EPS) {
                    que.add(i);
                    visited[i] = true;
                }
            }
        }
        return false;
    }

    static class Interval implements Comparable<Interval> {
        double l, r;
        Interval(double l, double r) { this.l = l; this.r = r; }
        public int compareTo(Interval o) {
            return Double.compare(l, o.l);
        }
    }

    // Returns the segment [t1, t2] along the straight line from (xs,ys)->(xt,yt) that is inside the circle.
    // Parameter t is 0 at start, 1 at end.
    static Interval getInCircleInterval(double xs, double ys, double xt, double yt,
                                        double cx, double cy, double r) {
        double dx = xt - xs, dy = yt - ys;
        double fx = xs - cx, fy = ys - cy;
        double a = dx * dx + dy * dy;
        double b = 2 * (fx * dx + fy * dy);
        double c = fx * fx + fy * fy - r * r;
        double D = b * b - 4 * a * c;
        if (D < -EPS) return null;
        D = Math.max(0, D); // Clamp for precision
        double sqrtD = Math.sqrt(D);
        double t1 = (-b - sqrtD) / (2 * a);
        double t2 = (-b + sqrtD) / (2 * a);
        double l = Math.max(0, Math.min(t1, t2));
        double r_ = Math.min(1, Math.max(t1, t2));
        if (l > r_ - EPS) return null;
        // Convert t to distance
        double path_len = Math.hypot(dx, dy);
        return new Interval(l * path_len, r_ * path_len);
    }

    // Merge overlapping intervals
    static List<Interval> mergeIntervals(List<Interval> list) {
        List<Interval> res = new ArrayList<>();
        if (list.isEmpty()) return res;
        Collections.sort(list);
        double l = list.get(0).l, r = list.get(0).r;
        for (int i = 1; i < list.size(); i++) {
            Interval x = list.get(i);
            if (x.l <= r + EPS) {
                r = Math.max(r, x.r);
            } else {
                res.add(new Interval(l, r));
                l = x.l; r = x.r;
            }
        }
        res.add(new Interval(l, r));
        return res;
    }

    static double dist(double x1, double y1, double x2, double y2) {
        return Math.hypot(x1 - x2, y1 - y2);
    }
}