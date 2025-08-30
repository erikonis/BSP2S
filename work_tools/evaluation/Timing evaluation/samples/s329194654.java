import java.util.*;
public class Main {
	static Scanner sc = new Scanner(System.in);
	static int[][] edge;
	static int[] mincost;
	static boolean[] used;
	static int n, m;
	public static void main(String[] args) {
		while(read()){
			solve();
		}
	}
	
	static boolean read(){
		n = sc.nextInt();
		if(n == 0)return false;
		
		m = sc.nextInt();
		edge = new int[n][n];
		mincost = new int[n];
		used = new boolean[n];
		for(int[] format : edge)Arrays.fill(format, Integer.MAX_VALUE);
		Arrays.fill(mincost, Integer.MAX_VALUE);
		Arrays.fill(used, false);
		
		for(int i = 0; i < m; i++){
			String[] input = sc.next().split(",");
			edge[Integer.parseInt(input[0])][Integer.parseInt(input[1])] = (Integer.parseInt(input[2])-1)/100;
			edge[Integer.parseInt(input[1])][Integer.parseInt(input[0])] = (Integer.parseInt(input[2])-1)/100;
		}
		
		return true;
	}
	
	static void solve(){
		mincost[0] = 0;
		int res = 0;
		
		while(true){
			int v = -1;
			for(int i = 0; i < n; i++){
				if(!used[i] && (v == -1 || mincost[i] < mincost[v]))v = i;
			}
			
			if(v == -1)break;
			used[v] = true;
			res += mincost[v];
			
			for(int i = 0; i < n; i++){
				mincost[i] = Math.min(mincost[i], edge[v][i]);
			}
		}
		System.out.println(res);
	}

}