import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] sa = br.readLine().split(" ");
		int n = Integer.parseInt(sa[0]);

		sa = br.readLine().split(" ");
		int[] p = new int[n];
		for (int i = 0; i < n; i++) {
			p[i] = Integer.parseInt(sa[i]) - 1;
		}
		br.close();

		int[] d = new int[n];
		for (int i = 0; i < n; i++) {
			d[p[i]] = i;
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(20000 * (i + 1) + d[i]).append(' ');
		}
		sb.deleteCharAt(sb.length() - 1);
		System.out.println(sb.toString());

		sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(400000000 - 20000 * i).append(' ');
		}
		sb.deleteCharAt(sb.length() - 1);
		System.out.println(sb.toString());
	}
}
