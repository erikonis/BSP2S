import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int y1 = sc.nextInt();
            int m1 = sc.nextInt();
            int d1 = sc.nextInt();
            int y2 = sc.nextInt();
            int m2 = sc.nextInt();
            int d2 = sc.nextInt();

            if (y1 < 0 || m1 < 0 || d1 < 0 || y2 < 0 || m2 < 0 || d2 < 0) {
                break;
            }

            int days = 0;
            while (y1 != y2 || m1 != m2 || d1 != d2) {
                days++;
                d1++;
                int daysInMonth = getDaysInMonth(y1, m1);
                if (d1 > daysInMonth) {
                    d1 = 1;
                    m1++;
                    if (m1 > 12) {
                        m1 = 1;
                        y1++;
                    }
                }
            }
            System.out.println(days);
        }
        sc.close();
    }

    private static int getDaysInMonth(int year, int month) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                return isLeapYear(year) ? 29 : 28;
            default:
                return 0; // Should not happen
        }
    }

    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
}