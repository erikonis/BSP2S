import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Scanner;


public class Main {

	static char[][] grid = new char[1000][1000];
	static int[][] dist = new int[1000][1000];
	static int H;
	static int W;
	static int sx, sy, ex, ey;
	static Deque<Integer> xQ = new ArrayDeque<Integer>();
	static Deque<Integer> yQ = new ArrayDeque<Integer>();

	public int numMagic() {
		int res = 0;
		for (int i = 0; i < H; i++) {
			Arrays.fill(dist[i], Integer.MAX_VALUE);
		}
		dist[sx - 1][sy - 1] = 0;
		xQ.addFirst(sx - 1);
		yQ.addFirst (sy - 1);
		while (!xQ.isEmpty()) {
			
			int curX = xQ.poll();
			int curY = yQ.poll();
			//System.out.println("curX: "+curX + " curY: "+ curY);
			if (curX == ex - 1 && curY == ey - 1) {
				if(dist[curX][curY] < Integer.MAX_VALUE) {
					return dist[curX][curY];
				} else {
					return -1;
				}
			}

			int[] dx = { 0, 1, 0, -1 };
			int[] dy = { -1, 0, 1, 0 };

			for (int i = 0; i < 4; i++) {
				
					int x = curX + dx[i];
					int y = curY + dy[i];
					if (valid(x, y)) {
						if (dist[x][y] > dist[curX][curY]) {
							//System.out.println("adding ("+x+","+y+")");
							xQ.addFirst (x);
							yQ.addFirst(y);
							dist[x][y] = dist[curX][curY];
						}
					}
				
			}
			//System.out.println("After immediate curX: "+curX+ "  cury:  "+curY);
			for (int x = -2; x <= 2; x++) {
				for (int y = -2; y <= 2; y++) {
					if (valid(curX + x, curY + y)) {
						if (dist[curX + x][curY + y] > dist[curX][curY] + 1) {
							//System.out.println("adding ("+(curX + x)+","+(curY + y)+")");
							xQ.addLast(curX + x);
							yQ.addLast(curY + y);
							dist[curX + x][curY + y] = dist[curX][curY] + 1;
						}
					}
				}
			}

		}
		if(dist[ex-1][ey-1] < Integer.MAX_VALUE) {
			return dist[ex-1][ey-1];
		} else {
			return -1;
		}
	}

	public boolean valid(int x, int y) {
		boolean retVal = false;
		if (x < 0 || x >= H || y < 0 || y >= W || grid[x][y] == '#') {
			retVal =  false;
		} else {
			retVal =  true;
			//System.out.println("isValid: x: "+x + " y: "+y + " "+ retVal);
		}
		
		
		return retVal;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		H = sc.nextInt();
		W = sc.nextInt();
		sx = sc.nextInt();
		sy = sc.nextInt();
		ex = sc.nextInt();
		ey = sc.nextInt();

		String gridPts;
		for (int i = 0; i < H; i++) {
			gridPts = sc.next();
			grid[i] = gridPts.toCharArray();
		}
		Main sol = new Main();
		System.out.println(sol.numMagic());
	}
}