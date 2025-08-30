
import java.util.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;

public class Main {

	int INF = 1 << 28;
	int OFS = 100;
	int[] dx = {-1,0,1,0};
	int[] dy = {0,-1,0,1};

	void run() {
		Scanner sc = new Scanner(System.in);
		for(;;) {
			int n = sc.nextInt();
			if(n == 0) break;
			int h[][] = new int[OFS*2+1][OFS*2+1];
			int t[][] = new int[OFS*2+1][OFS*2+1];
			for(int i=0;i<n;i++) {
				Dice d = new Dice(sc.nextInt(), sc.nextInt());
				int x = OFS, y = OFS;
				while(true) {
					int[] rot = d.canRoll();
					debug(d.as);
					debug(rot);
					if( h[y+dy[rot[0]]][x+dx[rot[0]]] < h[y][x] ) {
						d.roll(rot[0]);
						x += dx[rot[0]]; y += dy[rot[0]];
						continue;
					}
					if( h[y+dy[rot[1]]][x+dx[rot[1]]] < h[y][x] ) {
						d.roll(rot[1]);
						x += dx[rot[1]]; y += dy[rot[1]];
						continue;
					}
					break;
				}
				h[y][x]++; t[y][x] = d.as[0];
			}
			int ans[] = new int[7]; 
			for(int i=0;i<2*OFS+1;i++) for(int j=0;j<2*OFS+1;j++) {
				ans[ t[i][j] ] ++;
			}
			for(int i=1;i<=6;i++) System.out.print( ans[i] + (i==6? "\n": " "));
		}
	}
	
	class Dice {
		// top, left, back, right, front, botom
		int[] as = {1, 2, 4, 5, 3, 6};
		Dice (int t, int f) {
			for(int i=0;i<4&&t!=as[0];i++) roll(0);
			for(int i=0;i<4&&t!=as[0];i++) roll(1);
			for(int i=0;i<4&&f!=as[4];i++) roll(4);
		}
		
		int[] canRoll() {
			int[] ret = new int[2];
			int c = 0, p=0;
			for(int i=1;i<=4;i++) if(c<as[i]) {
				c = as[i];
				p = i;
			}
			ret[0] = p-1; c=0;
			for(int i=1;i<=4;i++) if(i!=ret[0]+1&&c<as[i]) {
				c = as[i];
				p = i;
			}
			ret[1] = p-1;
			return ret;
		}
		
		void roll(int d) {
			if( d == 0 ) roll( 0, 1, 5, 3 );
			if( d == 1 ) roll( 0, 2, 5, 4 );
			if( d == 2 ) roll( 0, 3, 5, 1 );
			if( d == 3 ) roll( 0, 4, 5, 2 );
			if( d == 4 ) roll( 1, 2, 3, 4 );
		}
		
		void roll(int a, int b, int c, int d) {
			int tmp = as[d];
			as[d] = as[c]; as[c] = as[b]; as[b] = as[a]; as[a] = tmp;
		}
	}

	public static void main(String[] args) {
		new Main().run();
	}

	void debug(Object... os) {
//		System.err.println(Arrays.deepToString(os));
	}
}