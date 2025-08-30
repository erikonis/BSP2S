import java.util.*;

public class Main {
    static Map<String, Set<String>> memo = new HashMap<>();
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        int targetX = sc.nextInt();
        int targetY = sc.nextInt();
        
        Set<String> reachable = solve(s, 0, 0, 0, 0); // x, y, dir, index
        
        String target = targetX + "," + targetY;
        System.out.println(reachable.contains(target) ? "Yes" : "No");
    }
    
    static Set<String> solve(String s, int x, int y, int dir, int index) {
        if (index == s.length()) {
            Set<String> result = new HashSet<>();
            result.add(x + "," + y);
            return result;
        }
        
        String key = x + "," + y + "," + dir + "," + index;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        
        Set<String> result = new HashSet<>();
        
        if (s.charAt(index) == 'F') {
            // Move forward in current direction
            int newX = x, newY = y;
            if (dir == 0) newX++; // right
            else if (dir == 1) newY++; // up
            else if (dir == 2) newX--; // left
            else newY--; // down
            
            result.addAll(solve(s, newX, newY, dir, index + 1));
        } else { // 'T'
            // Try both clockwise and counterclockwise
            int clockwise = (dir + 1) % 4;
            int counterclockwise = (dir + 3) % 4;
            
            result.addAll(solve(s, x, y, clockwise, index + 1));
            result.addAll(solve(s, x, y, counterclockwise, index + 1));
        }
        
        memo.put(key, result);
        return result;
    }
}