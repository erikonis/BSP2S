import java.io.*;
import java.util.*;
import java.math.*;
import java.util.concurrent.*;

class Main
{
    static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	static FastScanner sc=new FastScanner(br);
    static PrintWriter out=new PrintWriter(System.out);
	static Random rnd=new Random();
	static HashSet<Integer> hs=new HashSet<>();
	static int[] arr=new int[]{400,800,1200,1600,2000,2400,2800,3200};
	
	static int get(int curr)
	{
		for(int i=0;i<arr.length;i++)
		{
			if(curr<arr[i]) return i;
		}
		
		return -1;
	}
	
    public static void main(String args[]) throws Exception
    {
		int n=sc.nextInt(),val=0;
		
		for(int i=0;i<n;i++)
		{
			int curr=sc.nextInt();
			
			if(curr>=3200)
			{
				val++;
			}
			else
			{
				hs.add(get(curr));
			}
		}
		
		out.println(Math.max(1,hs.size())+" "+(hs.size()+val));out.close();
    }
}
class FastScanner
{
    BufferedReader in;
    StringTokenizer st;

    public FastScanner(BufferedReader in) {
        this.in = in;
    }
	
    public String nextToken() throws Exception {
        while (st == null || !st.hasMoreTokens()) {
            st = new StringTokenizer(in.readLine());
        }
        return st.nextToken();
    }
	
	public String next() throws Exception {
		return nextToken().toString();
	}
	
    public int nextInt() throws Exception {
        return Integer.parseInt(nextToken());
    }

    public long nextLong() throws Exception {
        return Long.parseLong(nextToken());
    }

    public double nextDouble() throws Exception {
        return Double.parseDouble(nextToken());
    }
}