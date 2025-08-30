import java.util.Scanner;

public class PalindromicNumber {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        sc.close();

        // A three-digit number N can be represented as ABC, where A is the hundreds digit,
        // B is the tens digit, and C is the units digit.
        // For N to be a palindrome, A must be equal to C.
        int hundredsDigit = N / 100;
        int unitsDigit = N % 10;

        if (hundredsDigit == unitsDigit) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }
}