import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            int w = sc.nextInt();
            int d = sc.nextInt();
            
            if (w == 0 && d == 0) break;
            
            int[] front = new int[w];
            int[] side = new int[d];
            
            for (int i = 0; i < w; i++) {
                front[i] = sc.nextInt();
            }
            
            for (int i = 0; i < d; i++) {
                side[i] = sc.nextInt();
            }
            
            int totalCubes = 0;
            
            // For each position (i,j), the height is min(front[i], side[j])
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < d; j++) {
                    totalCubes += Math.min(front[i], side[j]);
                }
            }
            
            System.out.println(totalCubes);
        }
        
        sc.close();
    }
}