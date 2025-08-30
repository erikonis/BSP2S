import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        
        // Store intervals [a_i, b_i-1] representing bridges that can disconnect the dispute
        List<int[]> intervals = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            intervals.add(new int[]{a, b - 1}); // bridges are numbered 1 to N-1
        }
        
        // Sort intervals by end point
        intervals.sort((x, y) -> Integer.compare(x[1], y[1]));
        
        int bridges = 0;
        int lastCut = 0;
        
        // Greedy: always cut the rightmost bridge that covers current interval
        for (int[] interval : intervals) {
            if (lastCut < interval[0]) {
                // Need a new cut
                lastCut = interval[1];
                bridges++;
            }
        }
        
        System.out.println(bridges);
    }
}