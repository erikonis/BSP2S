import java.util.*;

public class Main {
    static class FenwickTree {
        int[] tree;
        int n;
        
        FenwickTree(int n) {
            this.n = n;
            tree = new int[n + 1];
        }
        
        void update(int i, int val) {
            for (; i <= n; i += i & -i) {
                tree[i] += val;
            }
        }
        
        int query(int i) {
            int sum = 0;
            for (; i > 0; i -= i & -i) {
                sum += tree[i];
            }
            return sum;
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        
        List<Integer> whitePos = new ArrayList<>();
        List<Integer> blackPos = new ArrayList<>();
        
        for (int i = 0; i < 2 * n; i++) {
            char color = sc.next().charAt(0);
            int number = sc.nextInt();
            
            if (color == 'W') {
                whitePos.add(i);
            } else {
                blackPos.add(i);
            }
        }
        
        long inversions = 0;
        
        // Count inversions for white balls
        inversions += countInversions(whitePos);
        
        // Count inversions for black balls
        inversions += countInversions(blackPos);
        
        System.out.println(inversions);
    }
    
    static long countInversions(List<Integer> positions) {
        int n = positions.size();
        if (n <= 1) return 0;
        
        // Coordinate compression
        List<Integer> sorted = new ArrayList<>(positions);
        Collections.sort(sorted);
        Map<Integer, Integer> compress = new HashMap<>();
        for (int i = 0; i < n; i++) {
            compress.put(sorted.get(i), i + 1);
        }
        
        FenwickTree ft = new FenwickTree(n);
        long inversions = 0;
        
        // Process from right to left
        for (int i = n - 1; i >= 0; i--) {
            int compressed = compress.get(positions.get(i));
            // Count how many elements with smaller compressed values are already processed
            inversions += ft.query(compressed - 1);
            ft.update(compressed, 1);
        }
        
        return inversions;
    }
}