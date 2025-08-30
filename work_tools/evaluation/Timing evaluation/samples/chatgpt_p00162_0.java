import java.util.*;
import java.io.*;

public class Main {
    static final int MAX = 1000000;
    static ArrayList<Integer> hammingNumbers = new ArrayList<>();

    // Precompute all hamming numbers up to MAX
    static void precompute() {
        TreeSet<Long> set = new TreeSet<>();
        Queue<Long> queue = new LinkedList<>();
        set.add(1L);
        queue.add(1L);

        while (!queue.isEmpty()) {
            long curr = queue.poll();
            if (curr > MAX) continue;
            hammingNumbers.add((int) curr);
            long[] factors = {2, 3, 5};
            for (long f : factors) {
                long next = curr * f;
                if (next <= MAX && set.add(next)) {
                    queue.add(next);
                }
            }
        }
        Collections.sort(hammingNumbers);
    }

    public static void main(String[] args) throws Exception {
        precompute();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.equals("0")) break;
            String[] parts = line.split("\\s+");
            int m = Integer.parseInt(parts[0]);
            int n = Integer.parseInt(parts[1]);
            int from = lowerBound(hammingNumbers, m);
            int to = upperBound(hammingNumbers, n);
            System.out.println(to - from);
        }
    }

    // Lower bound: first index >= val
    static int lowerBound(ArrayList<Integer> list, int val) {
        int l = 0, r = list.size();
        while (l < r) {
            int mid = (l + r) / 2;
            if (list.get(mid) >= val) r = mid;
            else l = mid + 1;
        }
        return l;
    }

    // Upper bound: first index > val
    static int upperBound(ArrayList<Integer> list, int val) {
        int l = 0, r = list.size();
        while (l < r) {
            int mid = (l + r) / 2;
            if (list.get(mid) > val) r = mid;
            else l = mid + 1;
        }
        return l;
    }
}