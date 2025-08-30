import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


public class Main {
	public static void main(String[] args) throws NumberFormatException,
			IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int count;
		int[] ice = new int[10];
		while ((count = Integer.parseInt(br.readLine())) > 0) {
			Arrays.fill(ice, 0);
			while (count-- > 0) {
				ice[Integer.parseInt(br.readLine())]++;
			}
			for (int b : ice) {
				if (b != 0) {
					char[] c = new char[b];
					Arrays.fill(c, '*');
					System.out.println(c);
				} else {
					System.out.println('-');
				}
			}
		}
	}
}