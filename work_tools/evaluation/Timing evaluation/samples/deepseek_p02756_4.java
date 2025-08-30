import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder(sc.next());
        int Q = sc.nextInt();
        boolean reversed = false;

        Deque<Character> deque = new ArrayDeque<>();
        for (char c : sb.toString().toCharArray()) {
            deque.addLast(c);
        }

        for (int i = 0; i < Q; i++) {
            int T = sc.nextInt();
            if (T == 1) {
                reversed = !reversed;
            } else {
                int F = sc.nextInt();
                char C = sc.next().charAt(0);
                if (F == 1) {
                    if (!reversed) {
                        deque.addFirst(C);
                    } else {
                        deque.addLast(C);
                    }
                } else {
                    if (!reversed) {
                        deque.addLast(C);
                    } else {
                        deque.addFirst(C);
                    }
                }
            }
        }

        StringBuilder res = new StringBuilder();
        while (!deque.isEmpty()) {
            res.append(deque.pollFirst());
        }
        if (reversed) {
            res.reverse();
        }
        System.out.println(res.toString());
    }
}