
import java.util.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;

public class Main {

	int INF = 1 << 28;

	void run() {
		Scanner sc = new Scanner(System.in);
		for(;;) {
			int n = sc.nextInt();
                        if(n==0) break;
			for(int i=0;i<n;i++) {
				int[] score = new int[3];
				for(int j=0;j<3;j++) score[j] = sc.nextInt();
				
				if( score[0] == 100 || score[1] == 100 || score[2] == 100 ||
					score[0] + score[1] >= 180 || score[0] + score[1] + score[2] >= 240) {
					System.out.println("A");
				}
				else if( score[0] + score[1] + score[2] >= 210 || 
						 ( score[0] + score[1] + score[2] >= 150 && (score[0] >= 80 || score[1] >= 80) ) ) {
					System.out.println("B");
				}
				else System.out.println("C");
			}
		}
	}

	public static void main(String[] args) {
		new Main().run();
	}

	void debug(Object... os) {
		System.err.println(Arrays.deepToString(os));
	}
}