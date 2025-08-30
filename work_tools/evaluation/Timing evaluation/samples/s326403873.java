import java.util.Scanner;

public class Main {
	private Scanner sc;
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		sc = new Scanner(System.in);
		
		while (sc.hasNext() == true) {
			int d = Integer.parseInt(sc.next());

			int sum = 0;
			for (int i = d; i < 600; i = i + d) {
				sum = sum + (int)Math.pow(i, 2) * d;
			}
			System.out.println(sum);
		}
	}
}