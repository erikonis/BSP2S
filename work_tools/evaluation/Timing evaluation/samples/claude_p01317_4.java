import java.util.*;
import java.io.*;

public class Main {
    static class Edge {
        int to, time;
        char type;
        
        Edge(int to, int time, char type) {
            this.to = to;
            this.time = time;
            this.type = type;
        }
    }
    
    static class State {
        int pos, ship, dist;
        
        State(int pos, int ship, int dist) {
            this.pos = pos;
            this.ship = ship;
            this.dist = dist;
        }
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        
        while (!(line = br.readLine()).equals("0 0")) {
            String[] parts = line.split(" ");
            int N = Integer.parseInt(parts[0]);
            int M = Integer.parseInt(parts[1]);
            
            List<List<Edge>> graph = new ArrayList<>();
            for (int i = 0; i <= N; i++) {
                graph.add(new ArrayList<>());
            }
            
            for (int i = 0; i < M; i++) {
                parts = br.readLine().split(" ");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                int t = Integer.parseInt(parts[2]);
                char type = parts[3].charAt(0);
                
                graph.get(x).add(new Edge(y, t, type));
                graph.get(y).add(new Edge(x, t, type));
            }
            
            int R = Integer.parseInt(br.readLine());
            parts = br.readLine().split(" ");
            int[] destinations = new int[R];
            for (int i = 0; i < R; i++) {
                destinations[i] = Integer.parseInt(parts[i]);
            }
            
            int totalTime = 0;
            int currentPos = destinations[0];
            int shipPos = destinations[0];
            
            for (int i = 1; i < R; i++) {
                int target = destinations[i];
                int shortestTime = findShortestPath(graph, N, currentPos, shipPos, target);
                totalTime += shortestTime;
                
                // Update positions - both postman and ship end up at target
                currentPos = target;
                shipPos = target;
            }
            
            System.out.println(totalTime);
        }
    }
    
    static int findShortestPath(List<List<Edge>> graph, int N, int startPos, int startShip, int target) {
        // dist[pos][ship] = minimum time to reach pos with ship at ship
        int[][] dist = new int[N + 1][N + 1];
        for (int i = 0; i <= N; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        
        PriorityQueue<State> pq = new PriorityQueue<>((a, b) -> a.dist - b.dist);
        dist[startPos][startShip] = 0;
        pq.offer(new State(startPos, startShip, 0));
        
        while (!pq.isEmpty()) {
            State current = pq.poll();
            
            if (current.dist > dist[current.pos][current.ship]) continue;
            if (current.pos == target) return current.dist;
            
            for (Edge edge : graph.get(current.pos)) {
                int newPos = edge.to;
                int newShip = current.ship;
                int newDist = current.dist + edge.time;
                
                if (edge.type == 'S') {
                    // Sea route - can only use if ship is at current position
                    if (current.ship != current.pos) continue;
                    newShip = newPos; // Ship moves with postman
                }
                
                if (newDist < dist[newPos][newShip]) {
                    dist[newPos][newShip] = newDist;
                    pq.offer(new State(newPos, newShip, newDist));
                }
            }
        }
        
        return -1; // Should not happen given problem constraints
    }
}