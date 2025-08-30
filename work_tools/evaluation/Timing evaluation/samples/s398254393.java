
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        new Main().compute();
    }

    void compute() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int scoreA = 0, scoreB = 0;
            int n = sc.nextInt();
            if (n == 0) {
                break;
            }
            for (int i = 0; i < n; i++) {
                int cardA = sc.nextInt();
                int cardB = sc.nextInt();
                if (cardA > cardB) {
                    scoreA += cardA + cardB;
                } else if (cardA < cardB) {
                    scoreB += cardA + cardB;
                } else {
                    scoreA += cardA;
                    scoreB += cardB;
                }
            }
            System.out.println(scoreA + " " + scoreB);
        }
    }
}
