
import java.util.*;
import java.io.*;

public class Main {
    static HashSet<Integer>[]adj;
    static ArrayList<int[]>comp;
    static boolean valid;
    static int[]color;
    static int[] dfs(int u){
        int[]res=new int[2];
        res[color[u]]++;
        for (int v:adj[u]){
            if (color[v]==color[u]){
                valid=false;
                return new int[2];
            }
            if (color[v]==-1){
                color[v]=1^color[u];
                int[]nxt=dfs(v);
                res[0]+=nxt[0];
                res[1]+=nxt[1];
            }
        }
        return res;
    }
    static int [][]memo;
    static int dp(int idx,int size){
        if (idx==comp.size()){
            int cc=adj.length-size;
            return size*(size-1)/2+cc*(cc-1)/2;
        }
        if (memo[size][idx]!=0)
            return memo[size][idx];
        return memo[size][idx]=Math.min(dp(idx+1,size+comp.get(idx)[0]),
                dp(idx+1,size+comp.get(idx)[1]));
    }
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        PrintWriter pw = new PrintWriter(System.out);
        int n = sc.nextInt();
        int m = sc.nextInt();
        adj= new HashSet[n];
        for (int i =0;i<n;i++){
            adj[i]=new HashSet<>();
            for (int j =0;j<n;j++) {
                if (i!=j)
                adj[i].add(j);
            }
        }
        while (m-->0){
            int u = sc.nextInt()-1;
            int v= sc.nextInt()-1;
            adj[u].remove(v);
            adj[v].remove(u);
        }
        color= new int[n];
        Arrays.fill(color,-1);
        valid=true;
        comp= new ArrayList<>();
        for (int i =0;i<n&&valid;i++){
            if (color[i]==-1){
                color[i]=0;
                comp.add(dfs(i));
            }
        }
        if (!valid){
            pw.println(-1);
        }
        else {
            memo= new int[n+1][n+1];
            pw.println(dp(0,0));
        }
        pw.flush();
    }

    static class Scanner {
        StringTokenizer st;
        BufferedReader br;

        public Scanner(InputStream s) {
            br = new BufferedReader(new InputStreamReader(s));
        }

        public Scanner(FileReader r) {
            br = new BufferedReader(r);
        }

        public String next() throws IOException {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(br.readLine());
            return st.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        public String nextLine() throws IOException {
            return br.readLine();
        }

        public double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        public int[] nextIntArr(int n) throws IOException {
            int[] arr = new int[n];
            for (int i = 0; i < arr.length; i++)
                arr[i] = nextInt();
            return arr;
        }

        public boolean ready() throws IOException {
            return br.ready();
        }

    }
}