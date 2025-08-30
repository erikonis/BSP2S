
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		TaskX solver = new TaskX();
		solver.solve(1, in, out);
		out.close();
	}

	static int maxN = 100;
	static String WHITE = ".";
	static String BLACK = "#";

	static class TaskX {
		public void solve(int testNumber, InputReader in, PrintWriter out) {

			int white = in.nextInt();
			int black = in.nextInt();

			String[][] map = new String[maxN][maxN];
			for (int i = 0; i < maxN; i++) {
				for (int j = 0; j < maxN; j++) {
					if (j <= 49) {
						map[i][j] = WHITE;
					} else {
						map[i][j] = BLACK;
					}
				}
			}

			white--;
			black--;

			for (int i = 1; i <= 99; i+=2) {
				for (int j = 1; j <= 48; j+=2) {
					if (black > 0) {
						map[i][j] = BLACK;
						black--;
					}
				}
			}

			for (int i = 1; i <= 99; i+=2) {
				for (int j = 51; j <= 98; j+=2) {
					if (white > 0) {
						map[i][j] = WHITE;
						white--;
					}
				}
			}

			StringBuffer sb = new StringBuffer();
			sb.append("100 100\n");
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					sb.append(map[i][j]);
				}
				sb.append("\n");
			}
			out.print(sb.toString());
		}
	}

	static class InputReader {
		BufferedReader in;
		StringTokenizer tok;

		public String nextString() {
			while (!tok.hasMoreTokens()) {
				try {
					tok = new StringTokenizer(in.readLine(), " ");
				} catch (IOException e) {
					throw new InputMismatchException();
				}
			}
			return tok.nextToken();
		}

		public int nextInt() {
			return Integer.parseInt(nextString());
		}

		public long nextLong() {
			return Long.parseLong(nextString());
		}

		public int[] nextIntArray(int n) {
			int[] res = new int[n];
			for (int i = 0; i < n; i++) {
				res[i] = nextInt();
			}
			return res;
		}

		public long[] nextLongArray(int n) {
			long[] res = new long[n];
			for (int i = 0; i < n; i++) {
				res[i] = nextLong();
			}
			return res;
		}

		public InputReader(InputStream inputStream) {
			in = new BufferedReader(new InputStreamReader(inputStream));
			tok = new StringTokenizer("");
		}

	}

}
