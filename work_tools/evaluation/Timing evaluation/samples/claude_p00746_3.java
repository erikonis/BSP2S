import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            int n = sc.nextInt();
            if (n == 0) break;
            
            // Store positions of squares
            Map<Integer, int[]> positions = new HashMap<>();
            positions.put(0, new int[]{0, 0}); // First square at origin
            
            // Process placement instructions
            for (int i = 1; i < n; i++) {
                int refSquare = sc.nextInt();
                int direction = sc.nextInt();
                
                int[] refPos = positions.get(refSquare);
                int[] newPos = new int[2];
                
                // Calculate new position based on direction
                switch (direction) {
                    case 0: // left
                        newPos[0] = refPos[0] - 1;
                        newPos[1] = refPos[1];
                        break;
                    case 1: // down
                        newPos[0] = refPos[0];
                        newPos[1] = refPos[1] - 1;
                        break;
                    case 2: // right
                        newPos[0] = refPos[0] + 1;
                        newPos[1] = refPos[1];
                        break;
                    case 3: // up
                        newPos[0] = refPos[0];
                        newPos[1] = refPos[1] + 1;
                        break;
                }
                
                positions.put(i, newPos);
            }
            
            // Find bounding box
            int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
            int minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;
            
            for (int[] pos : positions.values()) {
                minX = Math.min(minX, pos[0]);
                maxX = Math.max(maxX, pos[0]);
                minY = Math.min(minY, pos[1]);
                maxY = Math.max(maxY, pos[1]);
            }
            
            int width = maxX - minX + 1;
            int height = maxY - minY + 1;
            
            System.out.println(width + " " + height);
        }
        
        sc.close();
    }
}