import java.util.*;

public class Main {
	public static void main (String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int m = sc.nextInt();
		int l = sc.nextInt();
		int[][] fuels = new int[n][n];
		int[][] gass = new int[n][n];
		for (int i = 0; i < n; i++) {
		    Arrays.fill(fuels[i], Integer.MAX_VALUE / 2);
		    fuels[i][i] = 0;
		    Arrays.fill(gass[i], Integer.MAX_VALUE / 2);
		    gass[i][i] = 0;
		}
		for (int i = 0; i < m; i++) {
		    int a = sc.nextInt() - 1;
		    int b = sc.nextInt() - 1;
		    int c = sc.nextInt();
		    fuels[a][b] = c;
		    fuels[b][a] = c;
		}
		for (int i = 0; i < n; i++) {
		    for (int j = 0; j < n; j++) {
		        for (int k = 0; k < n; k++) {
		            fuels[j][k] = Math.min(fuels[j][k], fuels[j][i] + fuels[i][k]);
		        }
		    }
		}
		for (int i = 0; i < n; i++) {
		    for (int j = 0; j < n; j++) {
		        if (fuels[i][j] <= l) {
		            gass[i][j] = 1;
		        }
		    }
		}
		for (int i = 0; i < n; i++) {
		    for (int j = 0; j < n; j++) {
		        for (int k = 0; k < n; k++) {
		            gass[j][k] = Math.min(gass[j][k], gass[j][i] + gass[i][k]);
		        }
		    }
		}
		int q = sc.nextInt();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < q; i++) {
		    int a = sc.nextInt() - 1;
		    int b = sc.nextInt() - 1;
		    if (gass[a][b] >= Integer.MAX_VALUE / 2) {
		        sb.append(-1).append("\n");
		    } else {
		        sb.append(gass[a][b] - 1).append("\n");
		    }
		}
		System.out.print(sb);
   }
}
