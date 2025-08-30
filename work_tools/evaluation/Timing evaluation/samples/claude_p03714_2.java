import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] a = new int[3 * N];
        for (int i = 0; i < 3 * N; i++) {
            a[i] = sc.nextInt();
        }
        
        // For each possible split point, calculate max score
        long maxScore = Long.MIN_VALUE;
        
        // leftMax[i] = maximum sum of N elements from a[0] to a[i]
        long[] leftMax = new long[3 * N];
        // rightMin[i] = minimum sum of N elements from a[i] to a[3*N-1]
        long[] rightMin = new long[3 * N];
        
        // Calculate leftMax using min heap (keep largest N elements)
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        long sum = 0;
        for (int i = 0; i < 3 * N; i++) {
            if (minHeap.size() < N) {
                minHeap.offer(a[i]);
                sum += a[i];
            } else if (a[i] > minHeap.peek()) {
                sum -= minHeap.poll();
                minHeap.offer(a[i]);
                sum += a[i];
            }
            
            if (minHeap.size() == N) {
                leftMax[i] = sum;
            }
        }
        
        // Calculate rightMin using max heap (keep smallest N elements)
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        sum = 0;
        for (int i = 3 * N - 1; i >= 0; i--) {
            if (maxHeap.size() < N) {
                maxHeap.offer(a[i]);
                sum += a[i];
            } else if (a[i] < maxHeap.peek()) {
                sum -= maxHeap.poll();
                maxHeap.offer(a[i]);
                sum += a[i];
            }
            
            if (maxHeap.size() == N) {
                rightMin[i] = sum;
            }
        }
        
        // Find maximum score
        for (int i = N - 1; i < 2 * N; i++) {
            maxScore = Math.max(maxScore, leftMax[i] - rightMin[i + 1]);
        }
        
        System.out.println(maxScore);
    }
}