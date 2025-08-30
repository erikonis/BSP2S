import java.util.Scanner;

public class Main {
    static long[] layers = new long[51];
    static long[] patties = new long[51];
    
    static {
        layers[0] = 1;
        patties[0] = 1;
        for (int i = 1; i <= 50; i++) {
            layers[i] = 2 * layers[i-1] + 3;
            patties[i] = 2 * patties[i-1] + 1;
        }
    }
    
    static long solve(int level, long x) {
        if (level == 0) {
            return x > 0 ? 1 : 0;
        }
        
        if (x <= 0) return 0;
        if (x >= layers[level]) return patties[level];
        
        // Structure: B + (level-1) + P + (level-1) + B
        if (x <= 1) {
            return 0; // First bun
        } else if (x <= 1 + layers[level-1]) {
            return solve(level-1, x-1); // First sub-burger
        } else if (x <= 2 + layers[level-1]) {
            return patties[level-1] + 1; // First sub-burger + middle patty
        } else if (x <= 2 + 2*layers[level-1]) {
            return patties[level-1] + 1 + solve(level-1, x - 2 - layers[level-1]); // + second sub-burger
        } else {
            return patties[level]; // Everything including last bun
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        long X = sc.nextLong();
        System.out.println(solve(N, X));
    }
}