import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        
        Set<Long> edges = new HashSet<>();
        List<List<Integer>> adj = new ArrayList<>();
        
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }
        
        for (int i = 0; i < m; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            adj.get(a).add(b);
            adj.get(b).add(a);
            edges.add(getEdgeKey(a, b));
        }
        
        int added = 0;
        boolean changed = true;
        
        while (changed) {
            changed = false;
            List<Long> toAdd = new ArrayList<>();
            
            // Find all pairs at distance 3
            for (int start = 1; start <= n; start++) {
                int[] dist = new int[n + 1];
                Arrays.fill(dist, -1);
                Queue<Integer> q = new LinkedList<>();
                q.offer(start);
                dist[start] = 0;
                
                while (!q.isEmpty()) {
                    int u = q.poll();
                    if (dist[u] >= 3) break;
                    
                    for (int v : adj.get(u)) {
                        if (dist[v] == -1) {
                            dist[v] = dist[u] + 1;
                            q.offer(v);
                        }
                    }
                }
                
                // Check for vertices at distance 3
                for (int end = start + 1; end <= n; end++) {
                    if (dist[end] == 3) {
                        long key = getEdgeKey(start, end);
                        if (!edges.contains(key)) {
                            toAdd.add(key);
                        }
                    }
                }
            }
            
            // Add all possible edges
            for (long key : toAdd) {
                int[] vertices = getVertices(key);
                int u = vertices[0];
                int v = vertices[1];
                
                edges.add(key);
                adj.get(u).add(v);
                adj.get(v).add(u);
                added++;
                changed = true;
            }
        }
        
        System.out.println(added);
    }
    
    private static long getEdgeKey(int a, int b) {
        if (a > b) {
            int temp = a;
            a = b;
            b = temp;
        }
        return ((long)a << 32) | b;
    }
    
    private static int[] getVertices(long key) {
        int a = (int)(key >> 32);
        int b = (int)(key & 0xFFFFFFFFL);
        return new int[]{a, b};
    }
}