import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int N = sc.nextInt();
        Map<String, Integer> blueCount = new HashMap<>();
        
        // Count blue cards
        for (int i = 0; i < N; i++) {
            String s = sc.next();
            blueCount.put(s, blueCount.getOrDefault(s, 0) + 1);
        }
        
        int M = sc.nextInt();
        Map<String, Integer> redCount = new HashMap<>();
        
        // Count red cards
        for (int i = 0; i < M; i++) {
            String t = sc.next();
            redCount.put(t, redCount.getOrDefault(t, 0) + 1);
        }
        
        // Find all unique strings
        Set<String> allStrings = new HashSet<>();
        allStrings.addAll(blueCount.keySet());
        allStrings.addAll(redCount.keySet());
        
        int maxEarn = 0; // Option of not announcing anything
        
        // Calculate net gain for each string
        for (String str : allStrings) {
            int blue = blueCount.getOrDefault(str, 0);
            int red = redCount.getOrDefault(str, 0);
            int netGain = blue - red;
            maxEarn = Math.max(maxEarn, netGain);
        }
        
        System.out.println(maxEarn);
        sc.close();
    }
}