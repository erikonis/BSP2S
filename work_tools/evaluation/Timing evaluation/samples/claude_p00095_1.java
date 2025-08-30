import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int n = sc.nextInt();
        int maxFish = -1;
        int winnerId = Integer.MAX_VALUE;
        
        for (int i = 0; i < n; i++) {
            int participantId = sc.nextInt();
            int fishCount = sc.nextInt();
            
            if (fishCount > maxFish || (fishCount == maxFish && participantId < winnerId)) {
                maxFish = fishCount;
                winnerId = participantId;
            }
        }
        
        System.out.println(winnerId + " " + maxFish);
        sc.close();
    }
}