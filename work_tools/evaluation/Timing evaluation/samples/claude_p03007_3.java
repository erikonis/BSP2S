import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        
        // Find the maximum absolute value
        int maxAbs = 0;
        int maxAbsIdx = 0;
        for (int i = 0; i < n; i++) {
            if (Math.abs(a[i]) > Math.abs(a[maxAbs])) {
                maxAbs = a[i];
                maxAbsIdx = i;
            }
        }
        
        // Calculate the maximum possible result
        long result = 0;
        for (int i = 0; i < n; i++) {
            result += Math.abs(a[i]);
        }
        if (maxAbs < 0) result = -result;
        
        System.out.println(result);
        
        // Generate operations
        List<int[]> operations = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            current.add(a[i]);
        }
        
        // First, make all numbers except the one with max absolute value have the same sign as maxAbs
        for (int i = 0; i < n; i++) {
            if (i != maxAbsIdx && ((a[i] > 0) != (maxAbs > 0))) {
                // Need to flip sign of a[i]
                // Find a number with opposite sign to subtract from
                int oppositeIdx = -1;
                for (int j = 0; j < current.size(); j++) {
                    if ((current.get(j) > 0) != (a[i] > 0)) {
                        oppositeIdx = j;
                        break;
                    }
                }
                
                int x = current.get(oppositeIdx);
                int y = current.remove((int)i < oppositeIdx ? i : i);
                if (oppositeIdx > i) oppositeIdx--;
                current.remove(oppositeIdx);
                
                operations.add(new int[]{x, y});
                current.add(x - y);
            }
        }
        
        // Now combine all remaining numbers
        while (current.size() > 1) {
            int x = current.remove(current.size() - 1);
            int y = current.remove(current.size() - 1);
            operations.add(new int[]{x, y});
            current.add(x - y);
        }
        
        for (int[] op : operations) {
            System.out.println(op[0] + " " + op[1]);
        }
    }
}