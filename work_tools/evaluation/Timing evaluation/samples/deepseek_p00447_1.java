import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int m = scanner.nextInt();
            if (m == 0) break;
            
            int[][] constellation = new int[m][2];
            for (int i = 0; i < m; i++) {
                constellation[i][0] = scanner.nextInt();
                constellation[i][1] = scanner.nextInt();
            }
            
            int n = scanner.nextInt();
            Set<String> photoStars = new HashSet<>();
            int[][] photo = new int[n][2];
            for (int i = 0; i < n; i++) {
                photo[i][0] = scanner.nextInt();
                photo[i][1] = scanner.nextInt();
                photoStars.add(photo[i][0] + "," + photo[i][1]);
            }
            
            // Use first star in constellation to find possible matches
            int x0 = constellation[0][0];
            int y0 = constellation[0][1];
            
            for (int i = 0; i < n; i++) {
                int dx = photo[i][0] - x0;
                int dy = photo[i][1] - y0;
                
                boolean match = true;
                for (int j = 1; j < m; j++) {
                    int x = constellation[j][0] + dx;
                    int y = constellation[j][1] + dy;
                    if (!photoStars.contains(x + "," + y)) {
                        match = false;
                        break;
                    }
                }
                
                if (match) {
                    System.out.println(dx + " " + dy);
                    break;
                }
            }
        }
    }
}