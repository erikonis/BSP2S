import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        
        int rockCount = 0;
        int paperCount = 0;
        int score = 0;
        
        for (char c : s.toCharArray()) {
            if (c == 'g') { // Opponent plays Rock
                // Try to play Paper to win, but check constraint
                if (paperCount + 1 <= rockCount) {
                    paperCount++;
                    score++; // We win
                } else {
                    rockCount++;
                    // Tie, no score change
                }
            } else { // c == 'p', Opponent plays Paper
                // Play Rock to win
                rockCount++;
                score++; // We win
            }
        }
        
        System.out.println(score);
    }
}