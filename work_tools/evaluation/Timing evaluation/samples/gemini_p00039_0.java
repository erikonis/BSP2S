import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String roman = sc.next();
            System.out.println(romanToArabic(roman));
        }
        sc.close();
    }

    private static int romanToArabic(String roman) {
        int arabic = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            char c = roman.charAt(i);
            int currentValue = getRomanValue(c);

            if (currentValue < prevValue) {
                arabic -= currentValue;
            } else {
                arabic += currentValue;
            }
            prevValue = currentValue;
        }
        return arabic;
    }

    private static int getRomanValue(char romanChar) {
        switch (romanChar) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0; // Should not happen for valid input
        }
    }
}