import java.util.*;

public class Main {
    static class Program {
        int start, end, channel;
        
        Program(int start, int end, int channel) {
            this.start = start;
            this.end = end;
            this.channel = channel;
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int C = sc.nextInt();
        
        Program[] programs = new Program[N];
        for (int i = 0; i < N; i++) {
            int s = sc.nextInt();
            int t = sc.nextInt();
            int c = sc.nextInt();
            programs[i] = new Program(s, t, c);
        }
        
        // Build conflict graph
        boolean[][] conflicts = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (hasConflict(programs[i], programs[j])) {
                    conflicts[i][j] = true;
                    conflicts[j][i] = true;
                }
            }
        }
        
        // Find minimum coloring using greedy approach
        int[] colors = new int[N];
        Arrays.fill(colors, -1);
        
        int maxColor = 0;
        for (int i = 0; i < N; i++) {
            boolean[] used = new boolean[N];
            
            // Check which colors are used by conflicting programs
            for (int j = 0; j < N; j++) {
                if (conflicts[i][j] && colors[j] != -1) {
                    used[colors[j]] = true;
                }
            }
            
            // Find first available color
            int color = 0;
            while (color < N && used[color]) {
                color++;
            }
            
            colors[i] = color;
            maxColor = Math.max(maxColor, color);
        }
        
        System.out.println(maxColor + 1);
    }
    
    static boolean hasConflict(Program p1, Program p2) {
        if (p1.channel == p2.channel) {
            return false; // Same channel programs don't conflict
        }
        
        // Check if recording intervals overlap considering the 0.5 buffer
        // Program 1: records from p1.start to p1.end, blocks from (p1.start-0.5) to p1.end
        // Program 2: records from p2.start to p2.end, blocks from (p2.start-0.5) to p2.end
        
        // Convert to avoid floating point: multiply by 2
        // p1 blocks from (2*p1.start-1) to (2*p1.end)
        // p2 blocks from (2*p2.start-1) to (2*p2.end)
        
        int p1BlockStart = 2 * p1.start - 1;
        int p1BlockEnd = 2 * p1.end;
        int p2BlockStart = 2 * p2.start - 1;
        int p2BlockEnd = 2 * p2.end;
        
        // Check if intervals overlap
        return !(p1BlockEnd <= p2BlockStart || p2BlockEnd <= p1BlockStart);
    }
}