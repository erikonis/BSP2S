import java.io.*;
import java.util.*;
import java.math.*;
// import java.awt.Point;
 
public class Main {
    InputStream is;
    PrintWriter out;
    String INPUT = "";
 
    static int mod = 1_000_000_007;
    // int mod = 998244353;
    // long inf = Long.MAX_VALUE/2;
    int inf = Integer.MAX_VALUE/2;

    int MAX_N=100000;
    int MAX_LOG=(int)(Math.log(MAX_N)/Math.log(2))+1;
    int n;
    ArrayList<Integer>[]path;
    int root=0;
    //parent[k][v]:=頂点vから2^k回親を辿った時に到達する頂点
    int[][]parent;
    int[]depth;

    int[] pat;
    void solve(){
        n = ni();
        path = new ArrayList[n];
        for(int i = 0; i < n; i++){
            path[i] = new ArrayList<>();
        }
        for(int i = 0; i < n-1; i++){
            int s = ni()-1;
            int t = ni()-1;
            path[s].add(t);
            path[t].add(s);
        }
        int m = ni();
        int[][] cond = new int[m][2];
        for(int i = 0; i < m; i++){
            int u = ni()-1;
            int v = ni()-1;
            cond[i][0] = u;
            cond[i][1] = v;
        }
        parent=new int[MAX_LOG][n];
        depth=new int[n];
        init(n);
        pat = new int[n];
        for(int i = 0; i < m; i++){
            int u = cond[i][0];
            int v = cond[i][1];
            int p = lca(u,v);
            int cur = u;
            // System.err.println(u+" "+v+" "+p);
            while(true){
                if(cur==p) break;
                pat[cur] |= 1<<i;
                cur = parent[0][cur];
            }
            cur = v;
            while(true){
                if(cur==p) break;
                pat[cur] |= 1<<i;
                cur = parent[0][cur];
            }
        }
        long[][] dp = new long[n][1<<m];
        dp[0][0] = 1;
        for(int i = 0; i < n-1; i++){
            for(int j = 0; j < (1<<m); j++){
                dp[i+1][j] += dp[i][j];
                dp[i+1][j|pat[i+1]] += dp[i][j];
            }
        }
        out.println(dp[n-1][(1<<m)-1]);
    }


    void dfs(int v,int p,int d) {
	   parent[0][v]=p;
	   depth[v]=d;
	   for(int i=0;i<path[v].size();i++) {
		   if(path[v].get(i)!=p) {
			   dfs(path[v].get(i), v, d+1);
		   }
	   }
   }
   //初期化
   void init(int V) {
	   //parent[0]とdepthを初期化する
	   dfs(root,-1,0);
	   //parentを初期化する
	   for(int k=0;k+1<MAX_LOG;k++) {
		   for(int v=0;v<V;v++) {
			   if(parent[k][v]<0)parent[k+1][v]=-1;
			   else parent[k+1][v]=parent[k][parent[k][v]];
		   }
	   }
   }
   
   //uとvのLCAを求める
   int lca(int u, int v) {
	   //uとvの深さが同じになるまで親をたどる
	   if(depth[u]>depth[v]) {
		   int temp=u;
		   u=v;
		   v=temp;
	   }
	   for(int k=0;k<MAX_LOG;k++) {
		   if((((depth[v]-depth[u])>>k)&1)==1) {
			   v=parent[k][v];
		   }
	   }
	   if(u==v)return u;
	   //二分探索でLCAを求める
	   for(int k=MAX_LOG-1;k>=0;k--) {
		   if(parent[k][u]!=parent[k][v]) {
			   u=parent[k][u];
			   v=parent[k][v];
		   }
	   }
	   return parent[0][u];
   }

    void run() throws Exception
    {
        is = INPUT.isEmpty() ? System.in : new ByteArrayInputStream(INPUT.getBytes());
        out = new PrintWriter(System.out);
        long s = System.currentTimeMillis();
        solve();
        out.flush();
        if(!INPUT.isEmpty())tr(System.currentTimeMillis()-s+"ms");
    }
    
    public static void main(String[] args) throws Exception { new Main().run(); }
    
    private byte[] inbuf = new byte[1024];
    private int lenbuf = 0, ptrbuf = 0;
    
    private int readByte()
    {
        if(lenbuf == -1)throw new InputMismatchException();
        if(ptrbuf >= lenbuf){
            ptrbuf = 0;
            try { lenbuf = is.read(inbuf); } catch (IOException e) { throw new InputMismatchException(); }
            if(lenbuf <= 0)return -1;
        }
        return inbuf[ptrbuf++];
    }
    
    private boolean isSpaceChar(int c) { return !(c >= 33 && c <= 126); }
    private int skip() { int b; while((b = readByte()) != -1 && isSpaceChar(b)); return b; }
    
    private double nd() { return Double.parseDouble(ns()); }
    private char nc() { return (char)skip(); }
    
    private String ns()
    {
        int b = skip();
        StringBuilder sb = new StringBuilder();
        while(!(isSpaceChar(b) && b != ' ')){
            sb.appendCodePoint(b);
            b = readByte();
        }
        return sb.toString();
    }
    
    private char[] ns(int n)
    {
        char[] buf = new char[n];
        int b = skip(), p = 0;
        while(p < n && !(isSpaceChar(b))){
            buf[p++] = (char)b;
            b = readByte();
        }
        return n == p ? buf : Arrays.copyOf(buf, p);
    }
    
    private char[][] nm(int n, int m)
    {
        char[][] map = new char[n][];
        for(int i = 0;i < n;i++)map[i] = ns(m);
        return map;
    }
    
    private int[] na(int n)
    {
        int[] a = new int[n];
        for(int i = 0;i < n;i++)a[i] = ni();
        return a;
    }
    
    private int ni()
    {
        int num = 0, b;
        boolean minus = false;
        while((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'));
        if(b == '-'){
            minus = true;
            b = readByte();
        }
        
        while(true){
            if(b >= '0' && b <= '9'){
                num = num * 10 + (b - '0');
            }else{
                return minus ? -num : num;
            }
            b = readByte();
        }
    }
    
    private long nl()
    {
        long num = 0;
        int b;
        boolean minus = false;
        while((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'));
        if(b == '-'){
            minus = true;
            b = readByte();
        }
        
        while(true){
            if(b >= '0' && b <= '9'){
                num = num * 10 + (b - '0');
            }else{
                return minus ? -num : num;
            }
            b = readByte();
        }
    }
    
    private static void tr(Object... o) { System.out.println(Arrays.deepToString(o)); }
 
}
