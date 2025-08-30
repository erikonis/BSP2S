import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String s = sc.next();
        
        // Try all 4 combinations for first two animals
        String[] combinations = {"SS", "SW", "WS", "WW"};
        
        for (String combo : combinations) {
            char[] animals = new char[n];
            animals[0] = combo.charAt(0);
            animals[1] = combo.charAt(1);
            
            boolean valid = true;
            
            // Determine the rest of the animals
            for (int i = 1; i < n - 1; i++) {
                char current = animals[i];
                char prev = animals[i - 1];
                char response = s.charAt(i);
                
                // Determine what the next animal should be
                boolean neighborsAreSame;
                if (current == 'S') {
                    // Sheep tells truth
                    neighborsAreSame = (response == 'o');
                } else {
                    // Wolf lies
                    neighborsAreSame = (response == 'x');
                }
                
                if (neighborsAreSame) {
                    animals[i + 1] = prev;
                } else {
                    animals[i + 1] = (prev == 'S') ? 'W' : 'S';
                }
            }
            
            // Check if the assignment is consistent for all animals
            for (int i = 0; i < n; i++) {
                char current = animals[i];
                char left = animals[(i - 1 + n) % n];
                char right = animals[(i + 1) % n];
                char response = s.charAt(i);
                
                boolean neighborsAreSame = (left == right);
                boolean expectedResponse;
                
                if (current == 'S') {
                    // Sheep tells truth
                    expectedResponse = neighborsAreSame;
                } else {
                    // Wolf lies
                    expectedResponse = !neighborsAreSame;
                }
                
                char expectedChar = expectedResponse ? 'o' : 'x';
                if (expectedChar != response) {
                    valid = false;
                    break;
                }
            }
            
            if (valid) {
                System.out.println(new String(animals));
                return;
            }
        }
        
        System.out.println("-1");
    }
}