import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        List<Integer> cards = new ArrayList<>();
        for (int i = 1; i <= 2 * n; i++) {
            cards.add(i);
        }
        
        for (int i = 0; i < m; i++) {
            int k = sc.nextInt();
            if (k == 0) {
                List<Integer> a = new ArrayList<>(cards.subList(0, n));
                List<Integer> b = new ArrayList<>(cards.subList(n, 2 * n));
                cards.clear();
                for (int j = 0; j < n; j++) {
                    cards.add(a.get(j));
                    cards.add(b.get(j));
                }
            } else {
                List<Integer> a = new ArrayList<>(cards.subList(0, k));
                List<Integer> b = new ArrayList<>(cards.subList(k, 2 * n));
                cards.clear();
                cards.addAll(b);
                cards.addAll(a);
            }
        }
        
        for (int card : cards) {
            System.out.println(card);
        }
    }
}