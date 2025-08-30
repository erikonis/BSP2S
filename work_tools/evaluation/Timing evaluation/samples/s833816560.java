import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int numCount[] = new int[101];
		String inputData = br.readLine();
		int value = 0;

		while (inputData != null) {
			numCount[Integer.parseInt(inputData)] += 1;
			inputData = br.readLine();
		}

		for (int i = 1; i <= 100; i++) {
			if (numCount[i] > value) {
				value = numCount[i];
			}
		}

		for (int i = 1; i <= 100; i++) {
			if (numCount[i] == value) {
				System.out.println(i);
			}
		}

	}

}