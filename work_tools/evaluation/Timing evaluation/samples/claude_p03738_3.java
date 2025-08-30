import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Read the two large integers as strings and convert to BigInteger
        BigInteger A = new BigInteger(scanner.nextLine());
        BigInteger B = new BigInteger(scanner.nextLine());
        
        // Compare the two numbers
        int comparison = A.compareTo(B);
        
        if (comparison > 0) {
            System.out.println("GREATER");
        } else if (comparison < 0) {
            System.out.println("LESS");
        } else {
            System.out.println("EQUAL");
        }
        
        scanner.close();
    }
}