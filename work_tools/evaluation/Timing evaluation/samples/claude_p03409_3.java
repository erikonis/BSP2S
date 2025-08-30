import java.util.*;

public class Main {
    static class Point {
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    static List<List<Integer>> graph;
    static int[] match;
    static boolean[] used;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        
        Point[] red = new Point[n];
        Point[] blue = new Point[n];
        
        for (int i = 0; i < n; i++) {
            red[i] = new Point(sc.nextInt(), sc.nextInt());
        }
        
        for (int i = 0; i < n; i++) {
            blue[i] = new Point(sc.nextInt(), sc.nextInt());
        }
        
        // Build bipartite graph
        graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (red[i].x < blue[j].x && red[i].y < blue[j].y) {
                    graph.get(i).add(j);
                }
            }
        }
        
        // Maximum bipartite matching
        match = new int[n];
        Arrays.fill(match, -1);
        
        int result = 0;
        for (int i = 0; i < n; i++) {
            used = new boolean[n];
            if (dfs(i)) {
                result++;
            }
        }
        
        System.out.println(result);
    }
    
    static boolean dfs(int v) {
        for (int to : graph.get(v)) {
            if (used[to]) continue;
            used[to] = true;
            
            if (match[to] == -1 || dfs(match[to])) {
                match[to] = v;
                return true;
            }
        }
        return false;
    }
}