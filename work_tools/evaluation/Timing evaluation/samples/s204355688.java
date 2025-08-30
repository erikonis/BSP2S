import java.awt.geom.*;
import java.util.*;

public class Main {
	ArrayList<ArrayList<Integer>> pass;
	boolean [] used;
	int [] match;
	
	private int gcd(int a, int b){
		if(b == 0){
			return a;
		}
		else{
			return gcd(b, a%b);
		}
	}
	
	private boolean dfs(int v){
		used[v] = true;
		for(int i =0; i < pass.get(v).size(); i++){
			int u = pass.get(v).get(i);
			int w = match[u];
			if(w < 0 || !used[w] && dfs(w)){
				match[v] = u;
				match[u] = v;
				return true;
			}
		}
		return false;
	}

	private void doit() {
		Scanner sc = new Scanner(System.in);
		while(true){
			int m = sc.nextInt();
			int n = sc.nextInt();
			if(m ==0 && n == 0) break;
			int [] mdata = new int[m];
			int [] ndata = new int[n];
			used = new boolean[m + n];
			match = new int[m + n];
			
			for(int i =0; i < m; i++){
				mdata[i] = sc.nextInt();
			}
			for(int i =0; i < n; i++){
				ndata[i] = sc.nextInt();
			}
			pass = new ArrayList<ArrayList<Integer>>();
			for(int i =0; i  < m + n; i++){
				pass.add(new ArrayList<Integer>());
			}
			for(int i=0; i < m; i++){
				for(int j =0; j < n; j++){
					int  result = gcd(mdata[i], ndata[j]);
					if(result > 1){
						pass.get(i).add(m + j);
						pass.get(j + m).add(i);
					}
				}
			}
			
			int ans = 0;
			Arrays.fill(match, -1);
			for(int i=0; i < n + m; i++){
				if(match[i] < 0){
					Arrays.fill(used, false);
					if(dfs(i)){
						ans++;
					}
				}
			}
			//System.out.print("ANS= ");
			System.out.println(ans);
			
			//DEBUG
//			for(int i =0; i < m; i++){
//				ArrayList<Integer> al = pass.get(i);
//				for(int j=0; j < al.size(); j++){
//					System.out.print(al.get(j));
//				}
//				System.out.println();
//			}
//			for(int i =0; i < match.length; i++){
//				System.out.println(match[i]);
//			}
			
		}
		
	}

	public static void main(String[] args) {
		Main obj = new Main();
		obj.doit();
	}
}