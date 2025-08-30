import java.util.*;
import static java.lang.System.*;

public class Main {

	Scanner sc = new Scanner(in);
	
	void run() {
		int n;
		int[] coins = new int[4];
		n = sc.nextInt();
		if (n == 0) return;
		while (true) {
			int sum = 0;
			for (int i = 0; i < 4; i++) {
				coins[i] = sc.nextInt();
				sum += coins[i];
			}
			
			int[] ans = new int[4];
			int min = 10000;
			for (int t = 0; t <= coins[0]; t++) {
				for (int f = 0; f <= coins[1]; f++) {
					for (int oh = 0; oh <= coins[2]; oh++) {
						for (int fh = 0; fh <= coins[3]; fh++) {
							int ch, n10, n50, n100, n500;
							n10 = n50 = n100 = n500 = 0;
							if ((ch = 10*t+50*f+100*oh+500*fh-n) >= 0) {
								n500 = ch/500;
								n100 = (ch-500*n500)/100;
								n50 = (ch-500*n500-100*n100)/50;
								n10 = (ch-500*n500-100*n100-50*n50)/10;
								if (min > sum-t-f-oh-fh+n500+n100+n50+n10) {
									min = sum-t-f-oh-fh+n500+n100+n50+n10;
									ans[0] = t;
									ans[1] = f;
									ans[2] = oh;
									ans[3] = fh;
								}
							}
						}
					}
				}
			}
			
			if (ans[0] > 0) out.printf("10 %d\n", ans[0]);
			if (ans[1] > 0) out.printf("50 %d\n", ans[1]);
			if (ans[2] > 0) out.printf("100 %d\n", ans[2]);
			if (ans[3] > 0) out.printf("500 %d\n", ans[3]);
			
			n = sc.nextInt();
			if (n == 0) return;
			
			out.println();
		}
	}
	
	public static void main(String[] args) {
		new Main().run();
	}

}