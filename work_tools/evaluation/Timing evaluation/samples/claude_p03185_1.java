import java.util.*;

public class Main {
    static class Line {
        long m, b; // y = mx + b
        int idx;
        
        Line(long m, long b, int idx) {
            this.m = m;
            this.b = b;
            this.idx = idx;
        }
        
        long eval(long x) {
            return m * x + b;
        }
    }
    
    // Check if line2 is redundant when we have line1 and line3
    static boolean bad(Line line1, Line line2, Line line3) {
        // line2 is bad if intersection of line1,line3 <= intersection of line1,line2
        // (b3-b1)/(m1-m3) <= (b2-b1)/(m1-m2)
        // Cross multiply: (b3-b1)*(m1-m2) <= (b2-b1)*(m1-m3)
        return (line3.b - line1.b) * (line1.m - line2.m) <= (line2.b - line1.b) * (line1.m - line3.m);
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        long C = sc.nextLong();
        
        long[] h = new long[N];
        for (int i = 0; i < N; i++) {
            h[i] = sc.nextLong();
        }
        
        long[] dp = new long[N];
        dp[0] = 0;
        
        Deque<Line> hull = new ArrayDeque<>();
        hull.addLast(new Line(-2 * h[0], dp[0] + h[0] * h[0], 0));
        
        for (int j = 1; j < N; j++) {
            // Query: find minimum of dp[i] + (h[j] - h[i])^2 + C
            // = dp[i] + h[i]^2 - 2*h[i]*h[j] + h[j]^2 + C
            // = (dp[i] + h[i]^2) + (-2*h[i])*h[j] + h[j]^2 + C
            
            // Remove lines from front that are no longer optimal
            while (hull.size() >= 2) {
                Line first = hull.peekFirst();
                Line second = hull.peekFirst();
                hull.removeFirst();
                second = hull.peekFirst();
                hull.addFirst(first);
                
                if (first.eval(h[j]) <= second.eval(h[j])) {
                    break;
                } else {
                    hull.removeFirst();
                }
            }
            
            Line best = hull.peekFirst();
            dp[j] = best.eval(h[j]) + h[j] * h[j] + C;
            
            // Add new line for position j
            Line newLine = new Line(-2 * h[j], dp[j] + h[j] * h[j], j);
            
            // Remove lines from back that become redundant
            while (hull.size() >= 2) {
                Line last = hull.peekLast();
                hull.removeLast();
                Line secondLast = hull.peekLast();
                
                if (bad(secondLast, last, newLine)) {
                    // last is redundant, keep it removed
                } else {
                    hull.addLast(last);
                    break;
                }
            }
            
            hull.addLast(newLine);
        }
        
        System.out.println(dp[N-1]);
    }
}