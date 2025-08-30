import java.util.*;
import java.io.*;
import java.math.*;

public class Main {
	Scanner sc = new Scanner(System.in);

	void run() {
		char map[][] = new char[8][];

		for (int i = 0; i < 8; i++) {
			map[i] = sc.next().toCharArray();
		}

		boolean t = true;

		int di[] = { -1, 1, 0, 0, 1, -1, -1, 1 };
		int dj[] = { 0, 0, 1, -1, 1, 1, -1, -1 };
		for (;;) {
			int bmin = 0;
			{
				int minb = 0;
				int cj = 0;
				int ci = 0;
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {

						if (map[i][j] != '.') {
							continue;
						}

						int b = 0;
						for (int z = 0; z < 8; z++) {
							int ni = i + di[z];
							int nj = j + dj[z];

							if (ni >= 0 && ni < 8 && nj >= 0 && nj < 8) {
								int b2 = 0;
								if (map[ni][nj] == 'x') {
									b2++;
									for (;;) {
										ni += di[z];
										nj += dj[z];

										if (ni >= 0 && ni < 8 && nj >= 0
												&& nj < 8) {
										} else {
											b2 = 0;
											break;
										}
										if (map[ni][nj] == 'x') {
											b2++;
										}
										if (map[ni][nj] == 'o') {
											break;
										}
										if (map[ni][nj] == '.') {
											b2 = 0;
											break;
										}
									}
								}
								b += b2;
							}
						}
						if (b > minb) {
							minb = b;
							cj = j;
							ci = i;
						}
					}
				}

				if (minb > 0) {
					for (int z = 0; z < 8; z++) {
						int ni = ci + di[z];
						int nj = cj + dj[z];

						if (ni >= 0 && ni < 8 && nj >= 0 && nj < 8) {
							int b2 = 0;
							if (map[ni][nj] == 'x') {
								b2++;
								for (;;) {
									ni += di[z];
									nj += dj[z];
									if (ni >= 0 && ni < 8 && nj >= 0 && nj < 8) {
									} else {
										b2 = 0;
										break;
									}
									if (map[ni][nj] == 'x') {
										b2++;
									}
									if (map[ni][nj] == 'o') {
										break;
									}
									if (map[ni][nj] == '.') {
						
										b2 = 0;
										break;
									}
								}
							}
							if (b2 > 0) {
								ni = ci + di[z];
								nj = cj + dj[z];
								map[ci][cj] = 'o';
								for (int i2 = 0; i2 < b2; i2++) {
									// System.out.println(ni+" "+nj);
									map[ni][nj] = 'o';
									ni += di[z];
									nj += dj[z];
								}
							}
						}
					}
				}
				bmin = minb;
			}
			int minw = 0;
			{
				int minb = 0;
				int cj = 0;
				int ci = 0;
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {

						if (map[i][j] != '.') {
							continue;
						}

						int b = 0;
						for (int z = 0; z < 8; z++) {
							int ni = i + di[z];
							int nj = j + dj[z];

							if (ni >= 0 && ni < 8 && nj >= 0 && nj < 8) {
								int b2 = 0;
								if (map[ni][nj] == 'o') {
									b2++;
									for (;;) {
										ni += di[z];
										nj += dj[z];

										if (ni >= 0 && ni < 8 && nj >= 0
												&& nj < 8) {
										} else {
											b2 = 0;
											break;
										}
										if (map[ni][nj] == 'o') {
											b2++;
										}
										if (map[ni][nj] == 'x') {
											break;
										}
										if (map[ni][nj] == '.') {
											b2 = 0;
											break;
										}
									}
								}
								b += b2;
							}
						}
						if (b >= minb) {
							minb = b;
							cj = j;
							ci = i;
						}
					}
				}

				if (minb > 0) {
					for (int z = 0; z < 8; z++) {
						int ni = ci + di[z];
						int nj = cj + dj[z];

						if (ni >= 0 && ni < 8 && nj >= 0 && nj < 8) {
							int b2 = 0;
							if (map[ni][nj] == 'o') {
								b2++;
								for (;;) {
									ni += di[z];
									nj += dj[z];
									if (ni >= 0 && ni < 8 && nj >= 0 && nj < 8) {
									} else {
										b2 = 0;
										break;
									}
									if (map[ni][nj] == 'o') {
										b2++;
									}
									if (map[ni][nj] == 'x') {
										break;
									}
									if (map[ni][nj] == '.') {
										b2 = 0;
										break;
									}
								}
							}
							if (b2 > 0) {
								ni = ci + di[z];
								nj = cj + dj[z];
								map[ci][cj] = 'x';
								for (int i2 = 0; i2 < b2; i2++) {
									// System.out.println(ni+" "+nj);
									map[ni][nj] = 'x';
									ni += di[z];
									nj += dj[z];
								}
							}
						}
					}
				}
				minw = minb;
			}
			if (bmin + minw == 0) {
				break;
			}

		}

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		Main m = new Main();
		m.run();
	}

}