import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc  = new Scanner(System.in);
		int L = Integer.parseInt(sc.next());
		int R = Integer.parseInt(sc.next());
		int d = Integer.parseInt(sc.next());
		int x = R/d;
		int y = L/d;
		int answer=x-y;
		if(R%d==0&&L%d==0) {
			answer ++;
		}
		System.out.println(answer);

	}

}