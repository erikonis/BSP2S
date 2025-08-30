import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
	
	public static final int NUM = 100;
	public static long dfs(long[][][] memo, int n, int low, int s){
		if(n == 0){
			return s == 0 ? 1 : 0;
		}else if(low > NUM){
			return 0;
		}
		
		if(memo[n][low][s] >= 0){
			return memo[n][low][s];
		}
		
		long ret = 0;
		for(int next = low; next <= NUM; next++){
			if(s - next < 0){
				break;
			}
			
			ret += dfs(memo, n - 1, next + 1, s - next);
		}
		
		return memo[n][low][s] = ret;
	}
	
	public static final int N = 10;
	public static final int SIZE = 1000;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		long[][][] memo = new long[N + 1][NUM + 1][SIZE + 1];		
		for(int i = 0; i <= N; i++){
			for(int j = 0; j <= NUM; j++){
				for(int k = 0; k <= SIZE; k++){
					memo[i][j][k] = -1;
				}
			}
		}
		
		while(true){
			final int n = sc.nextInt();
			final int s = sc.nextInt();
			
			if(n == 0 && s == 0){
				break;
			}
			
			System.out.println(dfs(memo, n, 0, s));
		}
		
	}

}