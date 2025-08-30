import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	public static void main(String args[]) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			while (true) {
				String str = br.readLine();
				String s[] = str.split(" ");
				int n = Integer.parseInt(s[0]);
				int m = Integer.parseInt(s[1]);
				if (n == 0 && m == 0)
					break;
				int obj[] = new int[n];
				int sumObj[] = new int[n * n - n];
				int maxM = 0;
				String str2 = br.readLine();
				String s2[] = str2.split(" ");
				for (int i = 0; i < n; i++) {
					obj[i] = Integer.parseInt(s2[i]);
				}
				int count = 0;
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < n; j++) {
						if (i != j) {
							sumObj[count++] = obj[i] + obj[j];
						}
					}
				}
				Arrays.sort(sumObj);
				if (sumObj[0] > m) {
					System.out.println("NONE");
				} else {
					for (int i = 0; i < sumObj.length; i++) {
						if (sumObj[i] <= m)
							maxM = Math.max(maxM, sumObj[i]);
					}
					System.out.println(maxM);
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
