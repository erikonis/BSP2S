import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int n = sc.nextInt();
            int p = sc.nextInt();
            if (n == 0 && p == 0) break;

            int[] hand = new int[n];
            int bowl = p;
            int curr = 0;

            while (true) {
                if (bowl > 0) {
                    hand[curr]++;
                    bowl--;
                    // check for win
                    if (bowl == 0) {
                        boolean otherHave = false;
                        for (int i = 0; i < n; i++) {
                            if (i != curr && hand[i] > 0) {
                                otherHave = true;
                                break;
                            }
                        }
                        if (!otherHave) {
                            System.out.println(curr);
                            break;
                        }
                    }
                } else {
                    bowl = hand[curr];
                    hand[curr] = 0;
                }
                curr = (curr + 1) % n;
            }
        }
    }
}