import java.util.*;
import java.io.*;
import java.lang.reflect.Array;

import static java.lang.Math.*;
import static java.util.Arrays.*;
import static java.util.Collections.*;
 
public class Main{
    static final long mod=1000000007;
//    static int dx[]={-1,0,1,0};
//    static int dy[]={0,-1,0,1};

    public  static void main(String[] args)   throws Exception, IOException{        
        Reader sc = new Reader(System.in);
        PrintWriter out=new PrintWriter(System.out);

        int n=sc.nextInt();
        int x=sc.nextInt();
        int s[]=sc.nextIntArray(n);
        sort(s);
        
        long dp[][]=new long[100001][n+1];
        for (int i = 1; i <=100000 ; i++) {
        	dp[i][1]=i%s[0];
        }

        for (int i = 2; i <= n ; i++) {
        	for (int t = 1; t <= 100000; t++) {
				dp[t][i]=dp[t][i-1]*(i-1)%mod;
			}
        	for (int t = 1; t <= 100000; t++) {
				dp[t][i]+=dp[t%s[i-1]][i-1];
				dp[t][i]%=mod;
			}        	
		}

        out.println(dp[x][n]);
        out.flush();
    }



    static boolean validpos(int x,int y,int r, int c){
        return x<r && 0<=x && y<c && 0<=y;
    }

    static void db(Object... os){
        System.err.println(Arrays.deepToString(os));
    }  
}

//class P {
//    int id, d;
//    P(int  id, int d) {
//        this.id=id;
//        this.d=d;
//    }
//}

class P implements Comparable<P>{
    int id,T;
    P(int id, int T) {
        this.id=id;
        this.T=T;
    }
    public int compareTo(P p){
        return id-p.id; //des
    }
}

class Reader
{ 
    private BufferedReader x;
    private StringTokenizer st;
    
    public Reader(InputStream in)
    {
        x = new BufferedReader(new InputStreamReader(in));
        st = null;
    }
    public String nextString() throws IOException
    {
        while( st==null || !st.hasMoreTokens() )
            st = new StringTokenizer(x.readLine());
        return st.nextToken();
    }
    public int nextInt() throws IOException
    {
        return Integer.parseInt(nextString());
    }
    public int[] nextIntArray(int size) throws IOException{
    	int r[] = new int[size];
    	for (int i = 0; i < size; i++) {
			r[i] = this.nextInt(); 
		}
    	return r;
    }
    public long[] nextLongArray(int size) throws IOException{
    	long r[] = new long[size];
    	for (int i = 0; i < size; i++) {
			r[i] = this.nextLong(); 
		}
    	return r;
    }
    public long nextLong() throws IOException
    {
        return Long.parseLong(nextString());
    }
    public double nextDouble() throws IOException
    {
        return Double.parseDouble(nextString());
    }
}
