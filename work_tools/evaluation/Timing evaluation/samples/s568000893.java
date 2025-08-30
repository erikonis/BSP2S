import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		final String FIVE  = "5";
		final String SEVEN = "7";
		
		Scanner scanner = new Scanner(System.in);		
		String input = scanner.nextLine();
		scanner.close();
		
		String[] arrStr = (input.split(" ",0));

		int fivCnt = 0;
		int sevCnt = 0;
		
		for(String num : arrStr) {
			if(num.equals(FIVE)) {
				fivCnt ++;
			} else if(num.equals(SEVEN)) {
				sevCnt ++;
			}
		}
		
		String res;
		if(fivCnt == 2 && sevCnt == 1) {
			res = "YES";
		}else {
			res= "NO";
		}
		System.out.println(res);
	}
}
