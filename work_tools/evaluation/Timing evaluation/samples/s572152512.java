import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String input = sc.next();
		char[] arrayc = input.toCharArray();
		int[] a = new int[arrayc.length];

		for(int i = 0; i < arrayc.length; i++) {
			a[i] = Character.getNumericValue(arrayc[i]);
		}

		if(a[0] + a[1] + a[2] + a[3] == 7) {
			System.out.println(a[0] + "+" + a[1] + "+" + a[2] + "+" + a[3] + "=" + "7");

		} else if(a[0] + a[1] + a[2] - a[3] == 7) {
			System.out.println(a[0] + "+" + a[1] + "+" + a[2] + "-" + a[3] + "=" + "7");

		} else if(a[0] + a[1] - a[2] + a[3] == 7) {
			System.out.println(a[0] + "+" + a[1] + "-" + a[2] + "+" + a[3] + "=" + "7");

		} else if(a[0] + a[1] - a[2] - a[3] == 7) {
			System.out.println(a[0] + "+" + a[1] + "-" + a[2] + "-" + a[3] + "=" + "7");

		} else if(a[0] - a[1] - a[2] - a[3] == 7) {
			System.out.println(a[0] + "-" + a[1] + "-" + a[2] + "-" + a[3] + "=" + "7");

		} else if(a[0] - a[1] - a[2] + a[3] == 7) {
			System.out.println(a[0] + "-" + a[1] + "-" + a[2] + "+" + a[3] + "=" + "7");

		} else if(a[0] - a[1] + a[2] - a[3] == 7) {
			System.out.println(a[0] + "-" + a[1] + "+" + a[2] + "-" + a[3] + "=" + "7");

		} else if(a[0] - a[1] + a[2] + a[3] == 7) {
			System.out.println(a[0] + "-" + a[1] + "+" + a[2] + "+" + a[3] + "=" + "7");
		}



	}
}