import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Combination of Number Sequences
 */
public class Main {

	static int[][] counts = new int[11][331];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = "";

		for (int i = 1; i < 11; i++) {
			solve(0, i, i, 0);
		}

		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null && !line.isEmpty()) {
			int n = Integer.parseInt(line.substring(0, line.indexOf(' ')));
			int s = Integer.parseInt(line.substring(line.indexOf(' ') + 1));
			if (s > 330) {
				sb.append(0).append('\n');
			} else {
				sb.append(counts[n][s]).append('\n');
			}
		}
		System.out.print(sb.toString());
	}

	static void solve(int n, int i, int m, int s) {
		if (i == 0) {
			counts[m][s]++;
			return;
		}
		for (int j = 0; j < 10; j++) {
			if ((n & 1 << j) == 0) {
				solve(n | 1 << j, i - 1, m, s + i * j);
			}
		}
	}
}