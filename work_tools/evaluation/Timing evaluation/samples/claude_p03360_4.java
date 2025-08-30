import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int A = sc.nextInt();
        int B = sc.nextInt();
        int C = sc.nextInt();
        int K = sc.nextInt();
        
        // Use priority queue as max heap
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        pq.add(A);
        pq.add(B);
        pq.add(C);
        
        // Perform K operations
        for (int i = 0; i < K; i++) {
            int max = pq.poll();
            pq.add(max * 2);
        }
        
        // Calculate sum
        int sum = 0;
        while (!pq.isEmpty()) {
            sum += pq.poll();
        }
        
        System.out.println(sum);
    }
}