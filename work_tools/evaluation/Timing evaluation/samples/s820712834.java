import java.util.*;
class Edge1{
    int u;
    int v;
    double cost;
    public Edge1(int u, int v, double cost){
        this.u = u;
        this.v = v;
        this.cost = cost;
    }
}
public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        Edge1[] edges = new Edge1[m];
        for(int i=0; i<n; i++){
            x[i] = sc.nextInt();
            y[i] = sc.nextInt();
        }
        double res = 0;
        for(int i=0; i<m; i++){
            int u = sc.nextInt()-1;
            int v = sc.nextInt()-1;
            double dis = Math.sqrt(Math.pow(x[u]-x[v], 2)+Math.pow(y[u]-y[v], 2));
            res += dis;
            edges[i] = new Edge1(u, v, dis);
        }
        Arrays.sort(edges, new Comparator<Edge1>() {
            @Override
            public int compare(Edge1 o1, Edge1 o2) {
                if(o2.cost>o1.cost) return 1;
                else if(o2.cost<o1.cost) return -1;
                return 0;
            }
        });
        int[] parent = new int[n];
        int[] rank = new int[n];
        for(int i=0; i<n; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
        double cur_res = 0;
        for(int i=0; i<m; i++){
            Edge1 e = edges[i];
            if(!same(e.u, e.v, parent)){
                unite(e.u, e.v, parent, rank);
                cur_res += e.cost;
            }
        }
        System.out.println(res-cur_res);
    }
    public static boolean same(int u, int v, int[] parent){
        int pu = find(u, parent);
        int pv = find(v, parent);
        if(pu == pv)return true;
        return false;
    }
    public static int find(int v, int[] parent){
        if(parent[v] == v) return v;
        return parent[v] = find(parent[v], parent);
    }
    public static void unite(int u, int v, int[] parent, int[] rank){
        int pu = find(u, parent);
        int pv = find(v, parent);
        if(pu==pv) return;
        if(rank[pu]<rank[pv]){
            parent[pu] = pv;
        }
        else{
            parent[pv] = pu;
            if(rank[pu] == rank[pv]) rank[pu]++;
        }
    }
}
