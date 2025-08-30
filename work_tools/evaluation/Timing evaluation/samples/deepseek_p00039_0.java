import java.util.HashMap;
import java.util.Scanner;

public class RomanToArabic {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashMap<Character, Integer> romanValues = new HashMap<>();
        romanValues.put('I', 1);
        romanValues.put('V', 5);
        romanValues.put('X', 10);
        romanValues.put('L', 50);
        romanValues.put('C', 100);
        romanValues.put('D', 500);
        romanValues.put('M', 1000);

        while (scanner.hasNextLine()) {
            String roman = scanner.nextLine().trim();
            if (roman.isEmpty()) break;

            int total = 0;
            int prevValue = 0;
            
            // Process from right to left
            for (int i = roman.length() - 1; i >= 0; i--) {
                int currentValue = romanValues.get(roman.charAt(i));
                if (currentValue < prevValue) {
                    total -= currentValue;
                } else {
                    total += currentValue;
                }
                prevValue = currentValue;
            }
            System.out.println(total);
        }
    }
}