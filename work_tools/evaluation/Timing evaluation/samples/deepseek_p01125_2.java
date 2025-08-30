import java.util.*;

public class MysteriousGems {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int N = scanner.nextInt();
            if (N == 0) break;
            
            Set<String> gems = new HashSet<>();
            for (int i = 0; i < N; i++) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                gems.add(x + "," + y);
            }
            
            int M = scanner.nextInt();
            int x = 10, y = 10;
            Set<String> collected = new HashSet<>();
            if (gems.contains(x + "," + y)) {
                collected.add(x + "," + y);
            }
            
            for (int i = 0; i < M; i++) {
                char d = scanner.next().charAt(0);
                int l = scanner.nextInt();
                
                int dx = 0, dy = 0;
                switch (d) {
                    case 'N': dy = 1; break;
                    case 'E': dx = 1; break;
                    case 'S': dy = -1; break;
                    case 'W': dx = -1; break;
                }
                
                for (int step = 0; step < l; step++) {
                    x += dx;
                    y += dy;
                    String pos = x + "," + y;
                    if (gems.contains(pos)) {
                        collected.add(pos);
                    }
                }
            }
            
            System.out.println(collected.size() == gems.size() ? "Yes" : "No");
        }
    }
}