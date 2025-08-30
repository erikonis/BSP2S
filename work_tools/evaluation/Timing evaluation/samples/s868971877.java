import java.util.Scanner;
import java.math.BigInteger;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    BigInteger A = sc.nextBigInteger();
    BigInteger B = sc.nextBigInteger();
    if (A.compareTo(B) > 0) System.out.println("GREATER"); 
    if (A.compareTo(B) < 0) System.out.println("LESS"); 
    if (A.compareTo(B) == 0) System.out.println("EQUAL"); 
  }
}