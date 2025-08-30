import java.util.*;

public class Main {
    static class State {
        int a, b, c, operations;
        
        State(int a, int b, int c, int operations) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.operations = operations;
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int A = sc.nextInt();
        int B = sc.nextInt();
        int C = sc.nextInt();
        int K = sc.nextInt();
        
        Queue<State> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        
        queue.offer(new State(A, B, C, 0));
        visited.add(A + "," + B + "," + C + "," + 0);
        
        while (!queue.isEmpty()) {
            State current = queue.poll();
            
            // Check if magic is successful
            if (current.b > current.a && current.c > current.b) {
                System.out.println("Yes");
                return;
            }
            
            // If we've used all operations, continue
            if (current.operations >= K) {
                continue;
            }
            
            // Try multiplying each card by 2
            int[][] nextStates = {
                {current.a * 2, current.b, current.c},
                {current.a, current.b * 2, current.c},
                {current.a, current.b, current.c * 2}
            };
            
            for (int[] next : nextStates) {
                String key = next[0] + "," + next[1] + "," + next[2] + "," + (current.operations + 1);
                if (!visited.contains(key)) {
                    visited.add(key);
                    queue.offer(new State(next[0], next[1], next[2], current.operations + 1));
                }
            }
        }
        
        System.out.println("No");
    }
}