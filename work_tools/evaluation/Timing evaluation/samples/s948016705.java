import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import static java.lang.Integer.parseInt;

/**
 * The Secret Number
 */
public class Main {

	static final String EMPTY = "";

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		String[] words;

		while ((line = br.readLine()) != null && !line.isEmpty()) {

			int W, H;
			W = parseInt(line.substring(0, line.indexOf(' ')));
			H = parseInt(line.substring(line.indexOf(' ') + 1));
			if ((W | H) == 0) break;

			char[][] numc;
			String[][] nums;

			numc = new char[H + 1][W + 1];
			nums = new String[H + 1][W + 1];

			for (int i = 0; i < numc.length; i++) {
				Arrays.fill(numc[i], 'X');
			}

			for (int i = 0; i < H; i++) {
				line = br.readLine();
				for (int j = 0; j < W; j++) {
					char c = line.charAt(j);
					numc[i][j] = (c <= '9' ? c : 'X');
				}
			}

			//solve
			for (int i = H - 1; i >= 0; i--) {
				for (int j = W - 1; j >= 0; j--) {
					if (numc[i][j] != 'X') {
						String s1 = (numc[i + 1][j] != 'X' ? nums[i + 1][j] : EMPTY);
						String s2 = (numc[i][j + 1] != 'X' ? nums[i][j + 1] : EMPTY);
						nums[i][j] = numc[i][j] + large(s1, s2);
					}
				}
			}

			String max = EMPTY;

			for (int i = 0; i < H; i++) {
				for (int j = 0; j < W; j++) {
					if (nums[i][j] != null) {
						nums[i][j] = nums[i][j].replaceFirst("^0+", "");
						max = large(max, nums[i][j]);
					}
				}
			}

			System.out.println(max);

		} //end while

	} //end main

	static String large(String s1, String s2) {

		if (s1.length() > s2.length()) return s1;
		if (s1.length() < s2.length()) return s2;
		if (s1.equals(s2)) return s1;

		String ret = EMPTY;
		for (int i = 0; i < s1.length(); i++) {
			if (s1.charAt(i) > s2.charAt(i)) {
				ret = s1;
				break;
			} else if (s1.charAt(i) < s2.charAt(i)) {
				ret = s2;
				break;
			}
		}
		return ret;
	}
}