import java.util.*;
import java.io.*;
public class Main {
	
	public static boolean[] vis;
	public static ArrayList<Integer>[] adj;
	public static int[] lis;
	public static int[] val;
	public static ArrayList<Integer> ls;
	
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
		//Scanner in = new Scanner(System.in);
		//int n = in.nextInt();
		int n = Integer.parseInt(f.readLine());
		adj = new ArrayList[n];
		val = new int[n];
		StringTokenizer st = new StringTokenizer(f.readLine());
		for (int i = 0; i < n; i++) {
			adj[i] = new ArrayList<Integer>();
			val[i] = Integer.parseInt(st.nextToken());//in.nextInt();
		}
		for (int i = 0; i < n-1; i++) {
			//int u = in.nextInt()-1, v = in.nextInt()-1;
			st = new StringTokenizer(f.readLine());
			int u = Integer.parseInt(st.nextToken())-1, v = Integer.parseInt(st.nextToken())-1;
			adj[u].add(v);
			adj[v].add(u);
		}
		//for (int i = 0; i < n; i++) System.out.println(i + ": " + adj[i]);
		
		ls = new ArrayList<Integer>();
		ls.add(val[0]);
		vis = new boolean[n];
		vis[0] = true;
		lis = new int[n];
		lis[0] = 1;
		for (int i = 0; i < adj[0].size(); i++) {
			dfs(adj[0].get(i));
		}
		for (int i = 0; i < n; i++) System.out.println(lis[i]);
	}
	
	public static void dfs(int i) {
		if (vis[i]) return;
		vis[i] = true;
		if (val[i] > ls.get(ls.size()-1)) {
			ls.add(val[i]);
			lis[i] = ls.size();
			for (int j = 0; j < adj[i].size(); j++) {
				dfs(adj[i].get(j));
			}
			ls.remove(ls.size()-1);
		} else {
			int low = 0, high = ls.size()-1;
			while (low < high) {
				int mid = (low+high)/2;
				if (val[i] > ls.get(mid)) low = mid+1;
				else high = mid;
			}
			int rep = ls.set(high, val[i]);
			lis[i] = ls.size();
			for (int j = 0; j < adj[i].size(); j++) {
				dfs(adj[i].get(j));
			}
			ls.set(high, rep);
		}
	}
}