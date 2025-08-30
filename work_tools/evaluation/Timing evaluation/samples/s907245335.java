import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

//Descrete Speed
public class Main{

	public static int n;
	public static int m;
	public static int start;
	public static int goal;

	public static double[][][] dist;
	public static int[][] r;
	public static int[][] limit;

	public static double dijkstra(){
		for(int i=0;i<=n;i++){
			for(int j=0;j<=n;j++){
				for(int k=0;k<31;k++)
					dist[i][j][k] = Integer.MAX_VALUE;
			}
		}
		dist[start][0][0] = 0;
		PriorityQueue<int[]> q = new PriorityQueue<int[]>(n, new Comparator<int[]>(){
			public int compare(int[] a, int[] b) {
				return dist[a[0]][a[1]][a[2]]-dist[b[0]][b[1]][b[2]]<0?-1:dist[a[0]][a[1]][a[2]]-dist[b[0]][b[1]][b[2]]>0?1:0;
			}
		});
		q.add(new int[]{start,0,0});
		while(!q.isEmpty()){
			int[] uu = q.poll();
			int u = uu[0];
			int pre = uu[1];
			int v = uu[2];
			if(u==goal&&v==1)return dist[u][pre][v];
			for(int i=1;i<=n;i++){
				if(i!=pre && r[u][i]!=0){
					for(int x=v-1;x<=v+1;x++){
						if(x<=0)continue;
						if(x>limit[u][i])continue;
						double nc = dist[u][pre][v] + r[u][i]*1.0/x;
						if(nc < dist[i][u][x]){
							dist[i][u][x] = nc;
							q.add(new int[]{i, u, x});
						}
					}
				}
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while(true){
			n = sc.nextInt();
			m = sc.nextInt();
			if(n==0&&m==0)break;
			dist = new double[n+1][n+1][31];
			r = new int[n+1][n+1];
			limit = new int[n+1][n+1];
			start = sc.nextInt();
			goal = sc.nextInt();
			for(int i=0;i<m;i++){
				int u = sc.nextInt();
				int v = sc.nextInt();
				int d = sc.nextInt();
				int l = sc.nextInt();
				r[u][v] = r[v][u] = d;
				limit[u][v] = limit[v][u] = l;
			}
			double ans = dijkstra();
			if(ans==-1)System.out.println("unreachable");
			else System.out.printf("%.5f\n", ans);
		}
	}
}