import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        int K = sc.nextInt();
        
        // Use TreeSet to automatically sort and remove duplicates
        TreeSet<String> substrings = new TreeSet<>();
        
        // Generate all substrings
        int n = s.length();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j <= n; j++) {
                substrings.add(s.substring(i, j));
            }
        }
        
        // Convert to list to access K-th element
        List<String> sortedSubstrings = new ArrayList<>(substrings);
        
        // Print K-th smallest (1-indexed, so K-1 for 0-indexed)
        System.out.println(sortedSubstrings.get(K - 1));
    }
}