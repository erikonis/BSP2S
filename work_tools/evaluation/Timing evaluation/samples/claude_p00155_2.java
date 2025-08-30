import java.util.*;

public class Main {
    static class Building {
        int id;
        int x, y;
        
        Building(int id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }
    }
    
    static class Node {
        int building;
        double distance;
        List<Integer> path;
        
        Node(int building, double distance, List<Integer> path) {
            this.building = building;
            this.distance = distance;
            this.path = new ArrayList<>(path);
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            int n = sc.nextInt();
            if (n == 0) break;
            
            Map<Integer, Building> buildings = new HashMap<>();
            
            for (int i = 0; i < n; i++) {
                int id = sc.nextInt();
                int x = sc.nextInt();
                int y = sc.nextInt();
                buildings.put(id, new Building(id, x, y));
            }
            
            int m = sc.nextInt();
            for (int i = 0; i < m; i++) {
                int start = sc.nextInt();
                int goal = sc.nextInt();
                
                List<Integer> path = findShortestPath(buildings, start, goal);
                if (path == null) {
                    System.out.println("NA");
                } else {
                    for (int j = 0; j < path.size(); j++) {
                        if (j > 0) System.out.print(" ");
                        System.out.print(path.get(j));
                    }
                    System.out.println();
                }
            }
        }
    }
    
    static double distance(Building a, Building b) {
        return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
    }
    
    static List<Integer> findShortestPath(Map<Integer, Building> buildings, int start, int goal) {
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> Double.compare(a.distance, b.distance));
        Set<Integer> visited = new HashSet<>();
        
        List<Integer> initialPath = new ArrayList<>();
        initialPath.add(start);
        pq.offer(new Node(start, 0, initialPath));
        
        while (!pq.isEmpty()) {
            Node current = pq.poll();
            
            if (visited.contains(current.building)) continue;
            visited.add(current.building);
            
            if (current.building == goal) {
                return current.path;
            }
            
            Building currentBuilding = buildings.get(current.building);
            
            for (Building nextBuilding : buildings.values()) {
                if (visited.contains(nextBuilding.id)) continue;
                
                double dist = distance(currentBuilding, nextBuilding);
                if (dist <= 50) {
                    List<Integer> newPath = new ArrayList<>(current.path);
                    newPath.add(nextBuilding.id);
                    pq.offer(new Node(nextBuilding.id, current.distance + dist, newPath));
                }
            }
        }
        
        return null;
    }
}