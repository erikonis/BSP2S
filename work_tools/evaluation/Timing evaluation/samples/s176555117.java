import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
	
	public static int dfs(int node, final int n, boolean[] visited, boolean[][] adj, int time, int[] arrival_time, int[] end_time){
		visited[node] = true;
		arrival_time[node] = time;
		
		for(int next = 0; next < n; next++){
			if(!adj[node][next]){
				continue;
			}else if(visited[next]){
				continue;
			}
			
			time = dfs(next, n, visited, adj, time + 1, arrival_time, end_time);
		}
		
		end_time[node] = time + 1;
		return end_time[node];
	}
	
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		
		final int n = sc.nextInt();
		
		boolean[][] adj = new boolean[n][n];
		
		for(int i = 0; i < n; i++){
			final int u = sc.nextInt() - 1;
			final int k = sc.nextInt();
			
			for(int j = 0; j < k; j++){
				final int v = sc.nextInt() - 1;
				
				adj[u][v] = true;
			}
		}
		
		int time = 1;
		boolean[] visited = new boolean[n];
		int[] arrival_time = new int[n];
		int[] end_time = new int[n];
		for(int i = 0; i < n; i++){
			if(!visited[i]){
				time = dfs(i, n, visited, adj, time, arrival_time, end_time) + 1;
			}
		}
		
		for(int i = 0; i < n; i++){
			System.out.println((i + 1) + " " + arrival_time[i] + " " + end_time[i]);
		}
	}

	public static class Scanner {
		private BufferedReader br;
		private StringTokenizer tok;

		public Scanner(InputStream is) throws IOException {
			br = new BufferedReader(new InputStreamReader(is));
		}

		private void getLine() throws IOException {
			while (!hasNext()) {
				tok = new StringTokenizer(br.readLine());
			}
		}

		private boolean hasNext() {
			return tok != null && tok.hasMoreTokens();
		}

		public String next() throws IOException {
			getLine();
			return tok.nextToken();
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		public long nextLong() throws IOException {
			return Long.parseLong(next());
		}

		public void close() throws IOException {
			br.close();
		}
	}

}