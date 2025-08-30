import java.io.*;
import java.util.*;
import java.math.*;

public class Main {

	Scanner sc = new Scanner(System.in);

	void run(){
		String buffer =sc.nextLine();
		Scanner sc2 = new Scanner(buffer);
		int h = sc2.nextInt();
		int w = sc2.nextInt();
		int[][] map = new int[h][w];
		for(int i = 0 ; i < h ; i++){
			buffer =sc.nextLine();
			for(int j = 0; j < w; j++){
				map[i][j] = buffer.charAt(j) - '0';
			}
		}
		
		int[][] dp = new int[h][w];
		for(int i = 0; i < h ; i++){
			Arrays.fill(dp[i] , Integer.MAX_VALUE >> 2);
		}
		dp[0][0] = map[0][0];
		
		for(int i =0 ; i < h ; i ++){
			for(int j = 0; j < w ; j++){
				if(i==0 && j == 0){
					continue;
				}
				if(i == 0){
					dp[i][j] = Math.min(dp[i][j-1] + map[i][j] , dp[i][j]);
					continue;
				}
				if(j == 0){
					dp[i][j] = Math.min(dp[i-1][j] + map[i][j] , dp[i][j]);
					continue;
				}
				dp[i][j] = Math.min(Math.min(dp[i][j-1] + map[i][j] , dp[i-1][j] + map[i][j] ),dp[i][j]);
			}
		}
		System.out.println(dp[h-1][w-1]);
	}
	
	public static void main(String[] args) {
		Main m = new Main();
		m.run();
	}

}