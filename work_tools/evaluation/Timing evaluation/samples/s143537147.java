
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String s = br.readLine();
		String[] array = s.split(" ");
		int a = Integer.parseInt(array[0]);
		int b = Integer.parseInt(array[1]);
		int c = Integer.parseInt(array[2]);

		if ((b - a) == (c - b)) {
			System.out.println("YES");
		} else {
			System.out.println("NO");
		}
	}
}
