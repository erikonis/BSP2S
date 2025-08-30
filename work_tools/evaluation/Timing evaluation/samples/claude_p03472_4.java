import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        long H = sc.nextLong();
        
        int[][] katanas = new int[N][2];
        for (int i = 0; i < N; i++) {
            katanas[i][0] = sc.nextInt(); // a_i
            katanas[i][1] = sc.nextInt(); // b_i
        }
        
        // Sort by throw damage in descending order
        Arrays.sort(katanas, (a, b) -> Integer.compare(b[1], a[1]));
        
        // Find max wield damage
        int maxWield = 0;
        for (int i = 0; i < N; i++) {
            maxWield = Math.max(maxWield, katanas[i][0]);
        }
        
        long minAttacks = Long.MAX_VALUE;
        long throwDamage = 0;
        
        // Try throwing 0 to N katanas
        for (int throws = 0; throws <= N; throws++) {
            if (throwDamage >= H) {
                minAttacks = Math.min(minAttacks, throws);
                break;
            }
            
            long remainingDamage = H - throwDamage;
            long wieldsNeeded = (remainingDamage + maxWield - 1) / maxWield; // ceiling division
            
            minAttacks = Math.min(minAttacks, throws + wieldsNeeded);
            
            if (throws < N) {
                throwDamage += katanas[throws][1];
            }
        }
        
        System.out.println(minAttacks);
    }
}