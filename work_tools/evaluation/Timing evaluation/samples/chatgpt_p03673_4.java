import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for(int i=0;i<n;i++) a[i] = sc.nextInt();

        Deque<Integer> deque = new ArrayDeque<>();
        boolean rev = false;
        for (int i=0;i<n;i++) {
            if (!rev)
                deque.addLast(a[i]);
            else
                deque.addFirst(a[i]);
            rev = !rev;
        }

        List<Integer> res = new ArrayList<>(n);
        if (!rev) {
            while (!deque.isEmpty()) res.add(deque.pollFirst());
        } else {
            while (!deque.isEmpty()) res.add(deque.pollLast());
        }
        for (int i=0;i<res.size();i++) {
            if (i>0) System.out.print(" ");
            System.out.print(res.get(i));
        }
        System.out.println();
    }
}