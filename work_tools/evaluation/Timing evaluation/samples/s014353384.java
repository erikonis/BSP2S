import java.io.IOException; 
import java.io.InputStream; 
import java.io.PrintWriter; 

class Main{ 

	static long factorial[];
	static long inv[];
	static long mod;

	static long inverse(long n){
		long res = 1;
		for(int i=0;i<32;++i){
			if(((mod-2L)&(1L<<i))>0)res=(res*n)%mod;
			n=(n*n)%mod;
		}
		return res%mod;
	}
	static long c(int n,int k){
		return ((factorial[n]*inv[k])%mod*inv[n-k])%mod;
	}

	static void solve(){ 
		int N = ni();
		int A = ni();
		int B = ni();
		long K = nl();
		mod = 998244353;
		long ans = 0;
		factorial = new long[N+1];
		factorial[0]=1;
		for(int i=1;i<=N;++i)factorial[i]=(factorial[i-1]*i)%mod;
		inv = new long[N+1];
		for(int i=0;i<=N;++i)inv[i]=inverse(factorial[i]);

		for(int i=0;i<=N;++i){
			int n = i;
			if((K-(n*(long)A))%(long)B!=0)continue;
			long m = (K-(n*(long)A))/(long)B;
			if(m>N || m<0)continue;
			ans = (ans+(c(N,n)*c(N,(int)m))%mod)%mod;
		}
		out.println(ans);

	} 
 
 
 
 
	public static void main(String[] args){ 
		 solve(); 
		 out.flush(); 
	 } 
	 private static InputStream in = System.in; 
	 private static PrintWriter out = new PrintWriter(System.out); 
 
	 private static final byte[] buffer = new byte[1<<15]; 
	 private static int ptr = 0; 
	 private static int buflen = 0; 
	 private static boolean hasNextByte(){ 
		 if(ptr<buflen)return true; 
		 ptr = 0; 
		 try{ 
			 buflen = in.read(buffer); 
		 } catch (IOException e){ 
			 e.printStackTrace(); 
		 } 
		 return buflen>0; 
	 } 
	 private static int readByte(){ if(hasNextByte()) return buffer[ptr++]; else return -1;} 
	 private static boolean isSpaceChar(int c){ return !(33<=c && c<=126);} 
	 private static int skip(){int res; while((res=readByte())!=-1 && isSpaceChar(res)); return res;} 
 
	 private static double nd(){ return Double.parseDouble(ns()); } 
	 private static char nc(){ return (char)skip(); } 
	 private static String ns(){ 
		 StringBuilder sb = new StringBuilder(); 
		 for(int b=skip();!isSpaceChar(b);b=readByte())sb.append((char)b); 
		 return sb.toString(); 
	 } 
	 private static int[] nia(int n){ 
		 int[] res = new int[n]; 
		 for(int i=0;i<n;++i)res[i]=ni(); 
		 return res; 
	 } 
	 private static long[] nla(int n){ 
		 long[] res = new long[n]; 
		 for(int i=0;i<n;++i)res[i]=nl(); 
		 return res; 
	 } 
	 private static int ni(){ 
		 int res=0,b; 
		 boolean minus=false; 
		 while((b=readByte())!=-1 && !((b>='0'&&b<='9') || b=='-')); 
		 if(b=='-'){ 
			 minus=true; 
			 b=readByte(); 
		 } 
		 for(;'0'<=b&&b<='9';b=readByte())res=res*10+(b-'0'); 
		 return minus ? -res:res; 
	 } 
	 private static long nl(){ 
		 long res=0,b; 
		 boolean minus=false; 
		 while((b=readByte())!=-1 && !((b>='0'&&b<='9') || b=='-')); 
		 if(b=='-'){ 
			 minus=true; 
			 b=readByte(); 
		 } 
		 for(;'0'<=b&&b<='9';b=readByte())res=res*10+(b-'0'); 
		 return minus ? -res:res; 
	} 
} 

