import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		String in = scanner.nextLine();
		int a = Integer.parseInt(in);
		
		int base = a;
		int sum = 0;
		int dig = 0;
		while (a > 0) {
			dig = a % 10;
			sum += dig;
			a = a / 10;
		}
		
		if (base % sum == 0) {
			System.out.println("Yes");
		} else {
			System.out.println("No");
		}
		
	}
		
}
