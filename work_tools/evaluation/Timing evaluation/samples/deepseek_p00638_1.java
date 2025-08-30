import java.util.*;

public class OldBridges {
    static class Island {
        int treasure;
        int maxCarry;
        
        Island(int treasure, int maxCarry) {
            this.treasure = treasure;
            this.maxCarry = maxCarry;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int n = scanner.nextInt();
            if (n == 0) break;
            
            Island[] islands = new Island[n];
            for (int i = 0; i < n; i++) {
                int treasure = scanner.nextInt();
                int maxCarry = scanner.nextInt();
                islands[i] = new Island(treasure, maxCarry);
            }
            
            // Sort islands based on maxCarry + treasure in descending order
            Arrays.sort(islands, (a, b) -> {
                int sumA = a.maxCarry;
                int sumB = b.maxCarry;
                return Integer.compare(sumB, sumA);
            });
            
            boolean possible = true;
            int totalTreasure = 0;
            for (Island island : islands) {
                totalTreasure += island.treasure;
            }
            
            int currentTreasure = 0;
            for (Island island : islands) {
                if (island.maxCarry < currentTreasure + island.treasure) {
                    possible = false;
                    break;
                }
                currentTreasure += island.treasure;
            }
            
            // Check if we can return to start
            if (possible) {
                // The last island must allow carrying all treasures back (maxCarry >= totalTreasure)
                // However, since we need to return, the minimal condition is that there exists a path where all bridges allow return
                // The original condition is sufficient
            } else {
                // Try alternative condition: if any island can be visited after collecting all others
                possible = false;
                for (int i = 0; i < n; i++) {
                    if (islands[i].maxCarry >= totalTreasure - islands[i].treasure) {
                        possible = true;
                        break;
                    }
                }
                if (possible) {
                    possible = false;
                    // Now check if other islands can be visited before this one, leaving this one last
                    int tempTotal = totalTreasure - islands[0].treasure;
                    if (islands[0].maxCarry >= tempTotal) {
                        possible = true;
                        for (int i = 1; i < n; i++) {
                            if (islands[i].maxCarry < (totalTreasure - islands[i].treasure)) {
                                possible = false;
                                break;
                            }
                        }
                    }
                }
            }
            
            System.out.println(possible ? "Yes" : "No");
        }
        scanner.close();
    }
}