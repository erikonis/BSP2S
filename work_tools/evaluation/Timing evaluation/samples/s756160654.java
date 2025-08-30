import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		long N = Long.valueOf(scan.next());
		long M = Long.valueOf(scan.next());
		long sum = 0;
		if(M - N * 2 < 0) {
			sum = M / 2;
			N -= M / 2;
			M = 0;
		} else {
			sum += N;
			M -= N * 2;
			N = 0;
		}
		
		sum += M / 4;
		
		System.out.println(sum);
		
		scan.close();

	}

}
