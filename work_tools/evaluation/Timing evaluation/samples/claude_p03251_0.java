import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int N = sc.nextInt();
        int M = sc.nextInt();
        int X = sc.nextInt();
        int Y = sc.nextInt();
        
        int maxX = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++) {
            int x = sc.nextInt();
            maxX = Math.max(maxX, x);
        }
        
        int minY = Integer.MAX_VALUE;
        for (int i = 0; i < M; i++) {
            int y = sc.nextInt();
            minY = Math.min(minY, y);
        }
        
        // Z must satisfy: X < Z <= Y and maxX < Z and minY >= Z
        // So: max(X+1, maxX+1) <= Z <= min(Y, minY)
        int leftBound = Math.max(X + 1, maxX + 1);
        int rightBound = Math.min(Y, minY);
        
        if (leftBound <= rightBound) {
            System.out.println("No War");
        } else {
            System.out.println("War");
        }
        
        sc.close();
    }
}