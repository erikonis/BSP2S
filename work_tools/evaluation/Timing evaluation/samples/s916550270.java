
import java.util.Scanner;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int K = Integer.parseInt(scan.nextLine());
		String S = scan.nextLine();
		String str = null;
		
		if (S.length() <= K) {
			System.out.println(S);
		}else {
			str = S.substring(0, K);
			System.out.println(str + "...");
		}
	}
}