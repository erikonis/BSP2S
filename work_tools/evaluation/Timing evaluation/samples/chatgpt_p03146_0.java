import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int s = sc.nextInt();

        HashMap<Integer, Integer> seen = new HashMap<>();
        int current = s;

        for (int i = 1; ; i++) {
            if (seen.containsKey(current)) {
                System.out.println(i);
                break;
            } else {
                seen.put(current, i);
            }
            if (current % 2 == 0) {
                current = current / 2;
            } else {
                current = 3 * current + 1;
            }
        }
    }
}