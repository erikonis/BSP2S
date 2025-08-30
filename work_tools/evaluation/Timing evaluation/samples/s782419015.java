

import java.util.Arrays;
import java.util.Scanner;

public class Main {

	private static Scanner in;
	
	private static int n;
	private static double[] p;
	private static double[][] dp;
	private static final int MOD = (int)1e9+7;
	
	public static void main(String[] args) {
		in = new Scanner(System.in);
		n = in.nextInt();
		p =  new double[n];
		for(int i=0; i<n; i++) {
			p[i] = in.nextDouble();
		}
		
		dp = new double[n+1][n+1];
		
		for(int i=0; i<dp.length; i++)
			Arrays.fill(dp[i], -1);
		
		System.out.println(solve(0, 0));
	
	}
	
	private static double solve(int i, int heads) {
		
		if (i==n) {
			if (2*heads>n) return 1;
			else return 0;
		}
		
		if (dp[i][heads]!=-1) return dp[i][heads];
		
		double ans = 0;
		
		ans = p[i]*solve(i+1, heads+1)+(1-p[i])*solve(i+1, heads);
		
		return dp[i][heads]=ans;
	}

}
