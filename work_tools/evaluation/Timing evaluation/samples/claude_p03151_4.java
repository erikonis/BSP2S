import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        
        long[] a = new long[n];
        long[] b = new long[n];
        
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextLong();
        }
        for (int i = 0; i < n; i++) {
            b[i] = sc.nextLong();
        }
        
        long sumA = 0, sumB = 0;
        for (int i = 0; i < n; i++) {
            sumA += a[i];
            sumB += b[i];
        }
        
        // Check if it's possible
        if (sumA < sumB) {
            System.out.println(-1);
            return;
        }
        
        // Calculate surplus and deficit
        long surplus = 0;
        int deficitCount = 0;
        
        for (int i = 0; i < n; i++) {
            if (a[i] >= b[i]) {
                surplus += a[i] - b[i];
            } else {
                deficitCount++;
            }
        }
        
        // Try to keep as many positions unchanged as possible
        // Sort positions with surplus by how much surplus they have
        List<Integer> surplusIndices = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (a[i] >= b[i]) {
                surplusIndices.add(i);
            }
        }
        
        // Calculate total deficit needed
        long totalDeficit = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] < b[i]) {
                totalDeficit += b[i] - a[i];
            }
        }
        
        // All deficit positions must be changed
        int changes = deficitCount;
        
        // Check if we can satisfy deficit with available surplus
        if (totalDeficit > surplus) {
            System.out.println(-1);
            return;
        }
        
        // Sort surplus positions by surplus amount (ascending)
        // We want to use positions with smallest surplus first to minimize changes
        surplusIndices.sort((i, j) -> Long.compare(a[i] - b[i], a[j] - b[j]));
        
        long remainingDeficit = totalDeficit;
        for (int idx : surplusIndices) {
            if (remainingDeficit <= 0) break;
            
            long availableSurplus = a[idx] - b[idx];
            if (remainingDeficit > 0) {
                changes++;
                remainingDeficit -= availableSurplus;
            }
        }
        
        System.out.println(changes);
    }
}