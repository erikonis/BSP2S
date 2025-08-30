import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String S = sc.next();
        int maxConsecutiveRainyDays = 0;
        int currentConsecutiveRainyDays = 0;

        for (int i = 0; i < S.length(); i++) {
            if (S.charAt(i) == 'R') {
                currentConsecutiveRainyDays++;
            } else {
                maxConsecutiveRainyDays = Math.max(maxConsecutiveRainyDays, currentConsecutiveRainyDays);
                currentConsecutiveRainyDays = 0;
            }
        }
        maxConsecutiveRainyDays = Math.max(maxConsecutiveRainyDays, currentConsecutiveRainyDays);
        System.out.println(maxConsecutiveRainyDays);
        sc.close();
    }
}