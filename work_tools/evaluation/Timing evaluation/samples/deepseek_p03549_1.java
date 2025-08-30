import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        
        // Calculate the expected number of submissions
        // Probability of all M cases passing is (1/2)^M, so expected trials is 2^M
        int expectedTrials = (int) Math.pow(2, M);
        
        // Time per submission: 1900ms per M cases and 100ms per (N-M) cases
        int timePerSubmission = M * 1900 + (N - M) * 100;
        
        // Total expected time is trials * time per submission
        System.out.println(expectedTrials * timePerSubmission);
    }
}