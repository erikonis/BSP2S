import java.util.Scanner;

public class VisionTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] leftCounts = new int[4]; // A, B, C, D
        int[] rightCounts = new int[4]; // A, B, C, D

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) break;
            
            String[] parts = line.split(" ");
            double left = Double.parseDouble(parts[0]);
            double right = Double.parseDouble(parts[1]);
            
            // Process left eye
            updateCount(leftCounts, left);
            // Process right eye
            updateCount(rightCounts, right);
        }
        
        // Output results
        for (int i = 0; i < 4; i++) {
            System.out.println(leftCounts[i] + " " + rightCounts[i]);
        }
    }
    
    private static void updateCount(int[] counts, double vision) {
        if (vision >= 1.1) {
            counts[0]++;
        } else if (vision >= 0.6) {
            counts[1]++;
        } else if (vision >= 0.2) {
            counts[2]++;
        } else {
            counts[3]++;
        }
    }
}