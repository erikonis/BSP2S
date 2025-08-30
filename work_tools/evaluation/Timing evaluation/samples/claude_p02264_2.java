import java.util.*;

public class Main {
    static class Process {
        String name;
        int time;
        
        Process(String name, int time) {
            this.name = name;
            this.time = time;
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int q = sc.nextInt();
        
        Queue<Process> queue = new LinkedList<>();
        
        for (int i = 0; i < n; i++) {
            String name = sc.next();
            int time = sc.nextInt();
            queue.offer(new Process(name, time));
        }
        
        int currentTime = 0;
        
        while (!queue.isEmpty()) {
            Process current = queue.poll();
            
            if (current.time <= q) {
                currentTime += current.time;
                System.out.println(current.name + " " + currentTime);
            } else {
                currentTime += q;
                current.time -= q;
                queue.offer(current);
            }
        }
        
        sc.close();
    }
}