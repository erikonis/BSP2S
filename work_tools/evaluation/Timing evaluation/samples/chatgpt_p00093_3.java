import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<List<Integer>> results = new ArrayList<>();
        while (true) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            if (a == 0 && b == 0) break;
            List<Integer> leapYears = new ArrayList<>();
            for (int y = a; y <= b; y++) {
                if (isLeapYear(y)) leapYears.add(y);
            }
            results.add(leapYears);
        }
        for (int i = 0; i < results.size(); i++) {
            List<Integer> leaps = results.get(i);
            if (leaps.isEmpty()) {
                System.out.println("NA");
            } else {
                for (int y : leaps) System.out.println(y);
            }
            if (i != results.size() - 1) System.out.println();
        }
    }

    static boolean isLeapYear(int y) {
        if (y % 4 != 0) return false;
        if (y % 100 == 0 && y % 400 != 0) return false;
        return true;
    }
}