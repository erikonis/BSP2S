import java.util.*;
public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while(true) {
			int n = sc.nextInt();
			int m = sc.nextInt();
			int l = sc.nextInt();
			int K = sc.nextInt();
			int a = sc.nextInt();
			int h = sc.nextInt();
			if(n == 0)break;
			int[] list = new int[l+2];
			
			for(int i = 0; i < l; i++) {
				list[i] = sc.nextInt();
			}
			list[l] = a;
			list[l+1] = h;
			int[][] d = new int[n][n];
			for(int i = 0; i < n; i++) {
				Arrays.fill(d[i], 2 << 27);
			}
			for(int i = 0; i < K; i++) {
				int x = sc.nextInt();
				int y = sc.nextInt();
				int t = sc.nextInt();
				d[x][y] = t;
				d[y][x] = t;
			}
			
			int[][] newd = new int[l+2][l+2];
			for(int i = 0; i < l+2; i++) {
				Arrays.fill(newd[i], 2 << 27);
			}
			
			for(int k = 0; k < n; k++) {
				for(int i = 0; i < n; i++) {
					for(int j = 0; j < n; j++) {
						d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
					}
				}
			}
			
			for(int i = 0; i < l+2; i++) {
				for(int j = 0; j < l+2; j++) {
					if(d[list[i]][list[j]] <= m)
					newd[i][j] = d[list[i]][list[j]];
				}
			}
			
			
			
			
			for(int k = 0; k < l+2; k++) {
				for(int i = 0; i < l+2; i++) {
					for(int j = 0; j < l+2; j++) {
						//if(d[list[i]][list[k]] + d[list[k]][list[j]] <= m)
						newd[i][j] = Math.min(newd[i][j], newd[i][k] + newd[k][j]);
					}
				}
			}
			
			int result = newd[l][l+1];
			if(result > m && result != 2 << 27) {
				result += (result -m);
			}
			if(result == 2 << 27) {
				System.out.println("Help!");
			}
			else 
			System.out.println(result);
		}
		
		
		
		
		
	}
}                  