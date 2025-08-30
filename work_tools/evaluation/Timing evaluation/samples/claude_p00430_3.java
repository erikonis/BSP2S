import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n;
        while ((n = sc.nextInt()) != 0) {
            generatePartitions(n, n, new ArrayList<>());
        }
        sc.close();
    }
    
    static void generatePartitions(int remaining, int maxValue, List<Integer> current) {
        if (remaining == 0) {
            // Print current partition
            for (int i = 0; i < current.size(); i++) {
                if (i > 0) System.out.print(" ");
                System.out.print(current.get(i));
            }
            System.out.println();
            return;
        }
        
        // Try all possible values from maxValue down to 1
        for (int i = Math.min(remaining, maxValue); i >= 1; i--) {
            current.add(i);
            generatePartitions(remaining - i, i, current);
            current.remove(current.size() - 1);
        }
    }
}