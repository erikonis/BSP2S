import java.io.*;
import java.util.*;

public class Main {
    static class Pair {
        int minBalance, totalBalance;
        Pair(int minBalance, int totalBalance) {
            this.minBalance = minBalance;
            this.totalBalance = totalBalance;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        List<Pair> positives = new ArrayList<>();
        List<Pair> negatives = new ArrayList<>();
        int sumTotal = 0;

        for (int i = 0; i < N; i++) {
            String s = br.readLine();
            int bal = 0, minBal = 0;
            for (char c : s.toCharArray()) {
                if (c == '(') bal++;
                else bal--;
                minBal = Math.min(minBal, bal);
            }
            if (bal >= 0) {
                positives.add(new Pair(minBal, bal));
            } else {
                // For negatives, process reverse for sorting
                negatives.add(new Pair(minBal - bal, bal));
            }
            sumTotal += bal;
        }

        positives.sort((a, b) -> a.minBalance - b.minBalance);
        negatives.sort((a, b) -> b.minBalance - a.minBalance);

        int cur = 0;
        boolean ok = true;
        for (Pair p : positives) {
            if (cur + p.minBalance < 0) {
                ok = false;
                break;
            }
            cur += p.totalBalance;
        }
        if (ok) {
            for (Pair p : negatives) {
                if (cur + p.minBalance < 0) {
                    ok = false;
                    break;
                }
                cur += p.totalBalance;
            }
        }

        System.out.println(ok && sumTotal == 0 ? "Yes" : "No");
    }
}