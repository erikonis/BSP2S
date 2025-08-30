import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int m = sc.nextInt();
            int n = sc.nextInt();
            if (m == 0 && n == 0) break;
            sc.nextLine();

            boolean[] alive = new boolean[m+1];
            Arrays.fill(alive, true);
            int aliveCount = m;

            int nextNum = 1;
            int curPlayer = 1;

            for (int i = 0; i < n; i++) {
                String s = sc.nextLine();

                // Find the next alive player
                while (!alive[curPlayer]) {
                    curPlayer++;
                    if (curPlayer > m) curPlayer = 1;
                }

                String correctSay;
                if (nextNum % 15 == 0) {
                    correctSay = "FizzBuzz";
                } else if (nextNum % 3 == 0) {
                    correctSay = "Fizz";
                } else if (nextNum % 5 == 0) {
                    correctSay = "Buzz";
                } else {
                    correctSay = String.valueOf(nextNum);
                }

                if (!s.equals(correctSay)) {
                    alive[curPlayer] = false;
                    aliveCount--;
                    // If only one left, break after this turn
                    if (aliveCount == 1) {
                        // Move i to n to skip further input
                        i++;
                        while (i < n) {
                            sc.nextLine();
                            i++;
                        }
                        break;
                    }
                } else {
                    nextNum++;
                }
                // Next player's turn
                curPlayer++;
                if (curPlayer > m) curPlayer = 1;
            }

            // Output alive players
            List<Integer> res = new ArrayList<>();
            for (int i = 1; i <= m; i++) {
                if (alive[i]) res.add(i);
            }
            for (int j = 0; j < res.size(); j++) {
                if (j > 0) System.out.print(" ");
                System.out.print(res.get(j));
            }
            System.out.println();
        }
    }
}