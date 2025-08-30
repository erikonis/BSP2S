import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();
        
        int maxPairs = (N - 1) * (N - 2) / 2;
        if (K > maxPairs) {
            System.out.println(-1);
            return;
        }
        
        List<String> edges = new ArrayList<>();
        for (int i = 2; i <= N; i++) {
            edges.add("1 " + i);
        }
        
        int pairsToRemove = maxPairs - K;
        int u = 2;
        int v = 3;
        while (pairsToRemove > 0) {
            edges.add(u + " " + v);
            v++;
            if (v > N) {
                u++;
                v = u + 1;
            }
            pairsToRemove--;
        }
        
        System.out.println(edges.size());
        for (String edge : edges) {
            System.out.println(edge);
        }
    }
}