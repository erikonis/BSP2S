import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();
        
        int[] a = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            a[i] = sc.nextInt();
        }
        
        // Calculate distance from each town to capital
        int[] dist = new int[N + 1];
        Arrays.fill(dist, -1);
        dist[1] = 0;
        
        // BFS to find distances
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (int i = 1; i <= N; i++) {
                if (a[i] == curr && dist[i] == -1) {
                    dist[i] = dist[curr] + 1;
                    queue.offer(i);
                }
            }
        }
        
        // Count towns at each distance
        Map<Integer, List<Integer>> distGroups = new HashMap<>();
        for (int i = 1; i <= N; i++) {
            distGroups.computeIfAbsent(dist[i], k -> new ArrayList<>()).add(i);
        }
        
        int changes = 0;
        
        for (int i = 1; i <= N; i++) {
            int currentDist = dist[i];
            int targetDist = currentDist - 1;
            
            if (K == 1) {
                // All should point to capital
                if (a[i] != 1) {
                    changes++;
                }
            } else if (currentDist == K) {
                // Towns at distance K should point to towns at distance K-1
                if (dist[a[i]] != K - 1) {
                    changes++;
                }
            } else if (currentDist < K) {
                // Towns at distance < K should point to towns at distance currentDist+1
                // But if currentDist+1 > K, they should point to maintain the structure
                if (currentDist == 0) {
                    // Capital should point to a town at distance 1 if K > 1
                    if (K > 1 && dist[a[i]] != 1) {
                        changes++;
                    }
                } else {
                    // Towns at distance d should point to towns at distance d+1
                    int nextDist = currentDist + 1;
                    if (nextDist <= K && dist[a[i]] != nextDist) {
                        changes++;
                    }
                }
            }
        }
        
        // Recalculate more carefully
        changes = 0;
        
        for (int i = 1; i <= N; i++) {
            boolean needsChange = false;
            
            if (K == 1) {
                if (a[i] != 1) needsChange = true;
            } else {
                // For K steps to reach capital from town i
                // Town i should point to a town that reaches capital in K-1 steps
                int targetSteps = K - 1;
                if (dist[a[i]] != targetSteps) {
                    needsChange = true;
                }
            }
            
            if (needsChange) changes++;
        }
        
        System.out.println(changes);
    }
}