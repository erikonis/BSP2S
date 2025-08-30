import java.util.Scanner;
public class Main {

	public static void main(String[] args) {
		Scanner stdIn = new Scanner(System.in);
		
		int n = stdIn.nextInt(); //n個の町
		
		int a = stdIn.nextInt(); //歩いた時の疲労度
		int b = stdIn.nextInt(); //テレポートによる疲労度
		
		int[]x = new int[n];
		
		for(int i = 0; i < n; i++) {
			x[i] = stdIn.nextInt();
		}
		
		long exhausted = 0;
		
		
		
		for(int i = 0; i < n-1; i++) {
			exhausted += ((x[i+1]-x[i])*(long)a) < b ?  ((x[i+1]-x[i])*(long)a): b;
		}
		System.out.println(exhausted);
		
		
		
		
	}

}
