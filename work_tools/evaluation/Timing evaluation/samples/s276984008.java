import java.util.Scanner;
public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int x = sc.nextInt();

		while(true) {
			boolean judge = true;
			for(int i = 2; i < Math.sqrt(x); i++) {
				if(x%i==0) {
					judge = false;
					x++;
					break;
				}
			}
			if(judge) {
				break;
			}
		}
		System.out.println(x);
	}

}