import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            int n = sc.nextInt();
            if (n == 0) break;
            
            List<Integer> validSnacks = new ArrayList<>();
            
            // Read snack data
            int[][] snacks = new int[n][4]; // [id, protein, fat, carb]
            for (int i = 0; i < n; i++) {
                snacks[i][0] = sc.nextInt(); // id
                snacks[i][1] = sc.nextInt(); // protein
                snacks[i][2] = sc.nextInt(); // fat
                snacks[i][3] = sc.nextInt(); // carb
            }
            
            // Read limits
            int P = sc.nextInt(); // max protein
            int Q = sc.nextInt(); // max fat
            int R = sc.nextInt(); // max carb
            int C = sc.nextInt(); // max calories
            
            // Check each snack
            for (int i = 0; i < n; i++) {
                int protein = snacks[i][1];
                int fat = snacks[i][2];
                int carb = snacks[i][3];
                int calories = protein * 4 + fat * 9 + carb * 4;
                
                // Check if within limits
                if (protein <= P && fat <= Q && carb <= R && calories <= C) {
                    validSnacks.add(snacks[i][0]);
                }
            }
            
            // Output result
            if (validSnacks.isEmpty()) {
                System.out.println("NA");
            } else {
                for (int id : validSnacks) {
                    System.out.println(id);
                }
            }
        }
        
        sc.close();
    }
}