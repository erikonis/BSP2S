import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int A = scanner.nextInt();
		int B = scanner.nextInt();

		if (A <= 8 && B <= 8) {
			System.out.println("Yay!");
		} else if (A >= (16-B)/2 || B >= (16-A)/2) {
			System.out.println(":(");
		}
	}

}