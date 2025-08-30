import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int N = sc.nextInt();
        int K = sc.nextInt();
        int Q = sc.nextInt();
        
        int[] correctAnswers = new int[N + 1]; // 1-indexed
        
        for (int i = 0; i < Q; i++) {
            int player = sc.nextInt();
            correctAnswers[player]++;
        }
        
        for (int i = 1; i <= N; i++) {
            int finalScore = K - Q + correctAnswers[i];
            if (finalScore > 0) {
                System.out.println("Yes");
            } else {
                System.out.println("No");
            }
        }
        
        sc.close();
    }
}