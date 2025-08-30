import java.util.Scanner;
public class Main{
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()){
			double velocity = sc.nextDouble();
			double t = velocity / 9.8;
			double y = 4.9 *  Math.pow(t, 2);
			double N = (y + 5) / 5;
			int ans = (int)N;
			if(ans < N) System.out.println(ans + 1);
			else System.out.println(ans);

		}
	}
}