import java.util.Scanner;

public class Main {

	static Scanner sc = new Scanner(System.in);
	static int H, W, N;

	public static void main(String[] arg) {
		System.out.println(solve() ? "YES" : "NO");
	}

	static boolean solve() {
		H = sc.nextInt();
		W = sc.nextInt();
		N = sc.nextInt();
		char[][] f = new char[H][W];
		char[][] cf = new char[H][W];
		for (int i = 0; i < H; ++i) {
			f[i] = sc.next().toCharArray();
		}
		for (int i = 0; i < H; ++i) {
			for (int j = 0; j < W - 1; ++j) {
				for (int k = 0; k < H; ++k) {
					for (int l = 0; l < W; ++l) {
						cf[k][l] = f[k][l];
					}
				}
				char tmp = cf[i][j];
				cf[i][j] = cf[i][j + 1];
				cf[i][j + 1] = tmp;
				if (simulate(cf)) {
					return true;
				}
			}
		}
		return false;
	}

	static boolean simulate(char[][] f) {
		fall(f);
		while (true) {
			if (!erase(f)) {
				break;
			}
			fall(f);
		}
		for (int i = 0; i < H; ++i) {
			for (int j = 0; j < W; ++j) {
				if (f[i][j] != '.') return false;
			}
		}
		return true;
	}

	static boolean erase(char[][] f) {
		boolean any = false;
		boolean[][] e = new boolean[H][W];
		for (int i = 0; i < H; ++i) {
			for (int j = 0; j <= W - N; ++j) {
				if (f[i][j] == '.') continue;
				boolean ok = true;
				for (int k = 1; k < N; ++k) {
					if (f[i][j + k] != f[i][j]) {
						ok = false;
					}
				}
				if (ok) {
					any = true;
					for (int k = 0; k < N; ++k) {
						e[i][j + k] = true;
					}
				}
			}
		}
		for (int i = 0; i < W; ++i) {
			for (int j = 0; j <= H - N; ++j) {
				if (f[j][i] == '.') continue;
				boolean ok = true;
				for (int k = 1; k < N; ++k) {
					if (f[j + k][i] != f[j][i]) {
						ok = false;
					}
				}
				if (ok) {
					any = true;
					for (int k = 0; k < N; ++k) {
						e[j + k][i] = true;
					}
				}
			}
		}
		for (int i = 0; i < H; ++i) {
			for (int j = 0; j < W; ++j) {
				if (e[i][j]) f[i][j] = '.';
			}
		}
		return any;
	}

	static void fall(char[][] f) {
		for (int i = 0; i < W; ++i) {
			int pos = H - 1;
			for (int j = H - 1; j >= 0; --j) {
				if (f[j][i] != '.') {
					f[pos--][i] = f[j][i];
				}
			}
			while (pos >= 0) {
				f[pos--][i] = '.';
			}
		}
	}

}