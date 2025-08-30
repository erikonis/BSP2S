import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int X = sc.nextInt();
        int Y = sc.nextInt();
        sc.close();

        long totalMoney = 0;

        // Money from Coding Contest
        if (X == 1) {
            totalMoney += 300000;
        } else if (X == 2) {
            totalMoney += 200000;
        } else if (X == 3) {
            totalMoney += 100000;
        }

        // Money from Robot Maneuver
        if (Y == 1) {
            totalMoney += 300000;
        } else if (Y == 2) {
            totalMoney += 200000;
        } else if (Y == 3) {
            totalMoney += 100000;
        }

        // Additional money for winning both
        if (X == 1 && Y == 1) {
            totalMoney += 400000;
        }

        System.out.println(totalMoney);
    }
}