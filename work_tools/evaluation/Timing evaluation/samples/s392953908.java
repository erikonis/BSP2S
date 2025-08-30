
import java.math.BigInteger;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		BigInteger A = new BigInteger(scan.nextLine());
		BigInteger B = new BigInteger(scan.nextLine());
		if(A.compareTo(B) == 1) {
			System.out.println("GREATER");
		} else if(A.compareTo(B) == -1){
			System.out.println("LESS");
		} else if(A.compareTo(B) == 0) {
			System.out.println("EQUAL");
		}
		scan.close();

	}

}
