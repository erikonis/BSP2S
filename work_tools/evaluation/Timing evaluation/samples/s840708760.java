import java.io.*;
import java.util.*;


public class Main {
    static HashMap<Integer,Integer>[] g; //<toRoom, keyNecessary>
    static int n,m;
    static long mod = 1000000000+7;
    static HashSet<Integer>[] tree;
    static int[][] edge;
    static int[] c,res;
    public static void main(String[] args) throws Exception {
        //Scanner sc = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] buf = reader.readLine().split(" ");
        n = Integer.parseInt(buf[0]);
        tree = new HashSet[n];
        for(int i=0;i<n;i++) tree[i]=new HashSet<>();
        edge = new int[n-1][];
        for(int i=0;i<n-1;i++){
            buf = reader.readLine().split(" ");
            int u = Integer.parseInt(buf[0])-1, v = Integer.parseInt(buf[1])-1;
            edge[i] = new int[]{u,v};
            tree[u].add(v); tree[v].add(u);
        }
        c = new int[n];
        buf = reader.readLine().split(" ");
        for(int i=0;i<n;i++) c[i]=Integer.parseInt(buf[i]);
        Arrays.sort(c);
        res = new int[n];
        LinkedList<Integer> que = new LinkedList<>();
        boolean[] vis = new boolean[n];
        for(int i=0;i<n;i++){
            if(tree[i].size()==1) {
                que.add(i);
                vis[i] = true;
            }
        }
        int idx = 0;
        while(que.size()>0){
            int now = que.poll();
            res[now] = c[idx++];
            for(int w:tree[now]){
                if(tree[w].contains(now)){
                    tree[w].remove(now);
                    if(tree[w].size()==1) que.add(w);
                }
            }
        }
        int sum = 0;
        for(int[] e:edge){
            int u = e[0], v = e[1];
            sum += Math.min(res[u],res[v]);
        }

        PrintWriter out = new PrintWriter(System.out);
        out.println(sum);
        for(int i=0;i<n;i++) out.print(res[i]+" ");
        out.flush();
    }
}

