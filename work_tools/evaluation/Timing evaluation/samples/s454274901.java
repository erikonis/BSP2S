import java.util.Scanner;
public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = Integer.parseInt(sc.next());
		if (n < 6) {
			System.out.println(n);
			return;
		}
		System.out.println(r(n, 0));
	}
	public static int r(int m, int ans) {
		if (m < 6) return ans + m;
		int a = 0;
		int i = 6;
		while (i * 6 <= m)
			i *= 6;
		int j = 9;
		while (j * 9 <= m)
			j *= 9;
		if (m > 8) {
			return Math.min(r(m - i, ans + 1), r(m - j, ans + 1));
		}
		else
			return r(m - i, ans + 1);
	}
}