import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {

	/** 黒のマスを表す文字 */
	private static final char BLACK = '#';
	/** 空のマスを表す文字 */
	private static final char EMPTY = 0;

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			int h = scanner.nextInt(), w = scanner.nextInt();
			char[][] a = new char[h][];
			IntStream.range(0, h).forEach(i -> a[i] = scanner.next().toCharArray());
			boolean[] hasBlack = new boolean[h];
			Arrays.fill(hasBlack, false);
			// 行を確認する
			IntStream.range(0, h).forEach(i -> {
				for (int j = 0; j < w; j++) {
					if (BLACK == a[i][j]) {
						hasBlack[i] = true;
						break;
					}
				}
				if (!hasBlack[i]) {
					Arrays.fill(a[i], EMPTY);
				}
			});
			IntStream.range(0, w).forEach(j -> {
				boolean hasBlackj = false;
				for (int i = 0; i < h; i++) {
					if (BLACK == a[i][j]) {
						hasBlackj = true;
						break;
					}
				}
				if (!hasBlackj) {
					IntStream.range(0, h).forEach(i -> a[i][j] = EMPTY);
				}
			});
			IntStream.range(0, h).filter(i -> hasBlack[i]).forEach(i -> {
				IntStream.range(0, w).filter(j -> EMPTY != a[i][j]).forEach(j -> System.out.print(a[i][j]));
				System.out.println();
			});
		}
	}
}
