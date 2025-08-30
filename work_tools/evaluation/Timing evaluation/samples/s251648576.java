import java.util.Scanner;

public class Main {
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		String str[];
		int cup[];

		cup = new int[3];
		cup[0] = 1;
		while (sc.hasNext()) {
			str = sc.next().split(",");
			int swap = cup[str[0].charAt(0) - 'A'];
			cup[str[0].charAt(0) - 'A'] = cup[str[1].charAt(0) - 'A'];
			cup[str[1].charAt(0) - 'A'] = swap;
		}
		for (int i = 0; i < 3; i++) {
			if (cup[i] == 1) {
				System.out.println((char)('A' + i));
			}
		}
	}
}