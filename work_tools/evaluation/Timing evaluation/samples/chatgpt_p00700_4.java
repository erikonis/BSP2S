import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int cavesProcessed = 0;
        while (cavesProcessed < N) {
            int x = 0, y = 0;
            int maxDistSq = 0;
            int maxX = 0, maxY = 0;
            while (true) {
                int dx = sc.nextInt();
                int dy = sc.nextInt();
                if (dx == 0 && dy == 0) break;
                x += dx;
                y += dy;
                int distSq = x * x + y * y;
                if (distSq > maxDistSq || (distSq == maxDistSq && x > maxX)) {
                    maxDistSq = distSq;
                    maxX = x;
                    maxY = y;
                }
            }
            System.out.println(maxX + " " + maxY);
            cavesProcessed++;
        }
    }
}