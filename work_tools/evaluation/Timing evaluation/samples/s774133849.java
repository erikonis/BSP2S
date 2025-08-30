import java.io.*;
import java.util.*;

public class Main {
	static ArrayList<Integer> adj[];

	static int k;
	static long[] a;
	static int b[];
	static int m;

	static class Pair implements Comparable<Pair> {
		int c;
		int d;
		public Pair(int b, int l) {
			c = b;
			d = l;
		}
		@Override
		public int compareTo(Pair o) {
		return o.d-this.d;
		}



	}

	static ArrayList<Pair> adjlist[];
	// static char c[];
	static long mod = (long) (998244353);
	static int V;
	static long INF = (long) 1E16;
	static int n;
	static char c[];
	static Pair p[];
static long grid[][];
static Scanner sc = new Scanner(System.in);
static PrintWriter out = new PrintWriter(System.out);
public static void main(String args[]) throws Exception {
	int k=sc.nextInt();
	while(k-->0) {
		out.print("ACL");
	}
	out.flush();
}
static void update(int idx,int val) {
	if(idx==0) {
		total-=a[0];
		if(idx+1<a.length)
		total-=Math.max(0,a[idx+1]-a[idx]);
		a[0]=val;
		total+=a[0];
		if(idx+1<a.length)
		total+=Math.max(0,a[idx+1]-a[idx]);
		
		return;
	}
	if(idx>0) {
		total-=Math.max(0,a[idx]-a[idx-1]);
	}
	 if(idx+1<a.length) {
		total-=Math.max(0,a[idx+1]-a[idx]);
	}
	a[idx]=val;
	 if(idx>0) {
		total+=Math.max(0,a[idx]-a[idx-1]);
	}
	 if(idx+1<a.length) {
		total+=Math.max(0,a[idx+1]-a[idx]);
	}
}
static int[] compreessarray(int c[]) {
	Stack<Integer> st=new Stack<>();
	for (int i = 0; i < c.length; i++) {
		if(st.isEmpty()||c[i]!=st.peek())
		st.add(c[i]);
	}
	b=new int[st.size()];
	for (int i = b.length-1; i>=0; i--) {
		b[i]=st.pop();
	}
	return b;
}
static int div4[];
static long [] reversearray(long a[]) {
	for (int i = 0; i <a.length/2; i++) {
		long temp=a[i];
		a[i]=a[a.length-i-1];
		a[a.length-i-1]=temp;
	}
	return a;
}
static int [] reversearray(int a[]) {
	for (int i = 0; i <=a.length/2; i++) {
		int temp=a[i];
		a[i]=a[a.length-i-1];
		a[a.length-i-1]=temp;
	}
	return a;
}
static class SegmentTree {		// 1-based DS, OOP
	
	int N; 			//the number of elements in the array as a power of 2 (i.e. after padding)
	int[] array, sTree, lazy;
	
	SegmentTree(int[] in)		
	{
		array = in; N = in.length - 1;
		sTree = new int[N<<1];		//no. of nodes = 2*N - 1, we add one to cross out index zero
		lazy = new int[N<<1];
		build(1,1,N);
	}
	
	void build(int node, int b, int e)	// O(n)
	{
		if(b == e)					
			sTree[node] = array[b];
		else						
		{
			int mid = b + e >> 1;
			build(node<<1,b,mid);
			build(node<<1|1,mid+1,e);
			sTree[node] = sTree[node<<1]+sTree[node<<1|1];
		}
	}
	
	
	void update_point(int index, int val)			// O(log n)
	{
		index += N - 1;				
		sTree[index] += val;			
		while(index>1)				
		{
			index >>= 1;
			sTree[index] = sTree[index<<1] + sTree[index<<1|1];		
		}
	}
	
	
	void update_range(int i, int j, int val)		// O(log n) 
	{
		update_range(1,1,N,i,j,val);
	}
	
	void update_range(int node, int b, int e, int i, int j, int val)
	{
		if(i > e || j < b)		
			return;
		if(b >= i && e <= j)		
		{
			sTree[node] += (e-b+1)*val;			
			lazy[node] += val;				
		}							
		else		
		{
			int mid = b + e >> 1;
			propagate(node, b, mid, e);
			update_range(node<<1,b,mid,i,j,val);
			update_range(node<<1|1,mid+1,e,i,j,val);
			sTree[node] = sTree[node<<1] + sTree[node<<1|1];		
		}
		
	}
	void propagate(int node, int b, int mid, int e)		
	{
		lazy[node<<1] += lazy[node];
		lazy[node<<1|1] += lazy[node];
		sTree[node<<1] += (mid-b+1)*lazy[node];		
		sTree[node<<1|1] += (e-mid)*lazy[node];		
		lazy[node] = 0;
	}
	
	int query(int i, int j)
	{
		return query(1,1,N,i,j);
	}
	
	int query(int node, int b, int e, int i, int j)	// O(log n)
	{
		if(i>e || j <b)
			return 0;		
		if(b>= i && e <= j)
			return sTree[node];
		int mid = b + e >> 1;
		propagate(node, b, mid, e);
		int q1 = query(node<<1,b,mid,i,j);
		int q2 = query(node<<1|1,mid+1,e,i,j);
		return q1 + q2;	
				
	}
}
static  TreeSet<Integer> ts=new TreeSet();
static HashMap<Integer, Integer> compress(int a[]) {
		 ts = new TreeSet<>();
		HashMap<Integer, Integer> hm = new HashMap<>();
		for (int x : a)
			ts.add(x);
		for (int x : ts) {
			hm.put(x, hm.size() + 1);
		}
		return hm;
	}	

	static  class FenwickTree { // one-based DS

		int n;
		int[] ft;
		
		FenwickTree(int size) { n = size; ft = new int[n+1]; }
		
		int rsq(int b) //O(log n)
		{
			int sum = 0;
			while(b > 0) { sum += ft[b]; b -= b & -b;}		//min?
			return sum;
		}
		
		int rsq(int a, int b) { return rsq(b) - rsq(a-1); }	
		
		void point_update(int k, int val)	//O(log n), update = increment
		{
			while(k <= n) { ft[k] += val; k += k & -k; }		//min?
		}
	}
	static ArrayList<Integer> euler=new ArrayList<>();
	

	static ArrayList<Integer> arr;

	
static long total;
static TreeMap<Integer,Integer> map1;

		static int zz;
//static int dp(int idx,int left,int state) {
//if(idx>=k-((zz-left)*2)||idx+1==n) {
//	return 0;
//}
//if(memo[idx][left][state]!=-1) {
//	return memo[idx][left][state];
//}
//int ans=a[idx+1]+dp(idx+1,left,0);
//if(left>0&&state==0&&idx>0) {
//	ans=Math.max(ans,a[idx-1]+dp(idx-1,left-1,1));
//}
//return memo[idx][left][state]=ans;
//}21
static HashMap<Integer,Integer> map;
static int maxa=0;
static int ff=123123;

static long[][] memo;


	static long modmod=998244353;
	static int dx[]= {1,-1,0,0};
	 static int dy[]= {0,0,1,-1};	
	static class BBOOK implements Comparable<BBOOK>{
		int t;
		int alice;
		int bob;
		public BBOOK(int x,int y,int z) {
		t=x;
		alice=y;
		bob=z;
		}
		@Override
		public int compareTo(BBOOK o) {
			return this.t-o.t;
		}
	}
	private static long lcm(long a2, long b2) {
	return (a2*b2)/gcd(a2,b2);
	}
	static class Edge implements Comparable<Edge>
	{
		int node;long cost ; long time; 
		
		Edge(int a, long b,long c) { node = a;	cost = b; time=c; }
		
		public int compareTo(Edge e){ return Long.compare(time,e.time);	}
	}
	
        static void sieve(int N)	// O(N log log N) 
	{
		isComposite = new int[N+1];					
		isComposite[0] = isComposite[1] = 1;			// 0 indicates a prime number
		primes = new ArrayList<Integer>();
 
		for (int i = 2; i <= N; ++i) 					//can loop till i*i <= N if primes array is not needed O(N log log sqrt(N)) 
			if (isComposite[i] == 0) 					//can loop in 2 and odd integers for slightly better performance
			{
				primes.add(i);
				if(1l * i * i <= N)
					for (int j = i * i; j <= N; j += i)	// j = i * 2 will not affect performance too much, may alter in modified sieve
						isComposite[j] = 1;
			}   
	}
    static TreeSet<Integer> factors;
    static TreeSet<Integer> primeFactors(int N)		// O(sqrt(N) / ln sqrt(N))
	{
		 factors = new TreeSet<Integer>();		//take abs(N) in case of -ve integers
		int idx = 0, p = primes.get(idx);

		while(1l*p * p <= N)
		{
			while(N % p == 0) { factors.add(p); N /= p; }
			p = primes.get(++idx);
		}

		if(N != 1)						// last prime factor may be > sqrt(N)
			factors.add(N);				// for integers whose largest prime factor has a power of 1
		return factors;
	}

	static class UnionFind {
		int[] p, rank, setSize;
		int numSets;
		int max[];

		public UnionFind(int N) {
			p = new int[numSets = N];
			rank = new int[N];
			setSize = new int[N];
			for (int i = 0; i < N; i++) {
				p[i] = i;
				setSize[i] = 1;
			}
		}

		public int findSet(int i) {
			return p[i] == i ? i : (p[i] = findSet(p[i]));
		}

		public boolean isSameSet(int i, int j) {
			return findSet(i) == findSet(j);
		}
		public int chunion(int i,int j, int x2) {
			if (isSameSet(i, j))
				return 0;
			numSets--;
			int x = findSet(i), y = findSet(j);
			int z=findSet(x2);
			p[x]=z;;
			p[y]=z;
		return x;
		}

		public void unionSet(int i, int j) {
			if (isSameSet(i, j))
				return;
			numSets--;
			int x = findSet(i), y = findSet(j);
			if (rank[x] > rank[y]) {
				p[y] = x;
				setSize[x] += setSize[y];


			} else {
				p[x] = y;
				setSize[y] += setSize[x];
				if (rank[x] == rank[y])
					rank[y]++;


			}
		}

		

		public int numDisjointSets() {
			return numSets;
		}

		public int sizeOfSet(int i) {
			return setSize[findSet(i)];
		}
	}


	static class Quad implements Comparable<Quad> {
		int u;
		int v;
		char state;
		int turns;

		public Quad(int i, int j, char c, int k) {
			u = i;
			v = j;
			state = c;
			turns = k;
		}

		public int compareTo(Quad e) {
			return (int) (turns - e.turns);
		}

	}

	static long manhatandistance(long x, long x2, long y, long y2) {
		return Math.abs(x - x2) + Math.abs(y - y2);
	}

	static long fib[];

	static long fib(int n) {
		if (n == 1 || n == 0) {
			return 1;
		}
		if (fib[n] != -1) {
			return fib[n];
		} else
			return fib[n] = ((fib(n - 2) % mod + fib(n - 1) % mod) % mod);
	}

	static class Point  implements Comparable<Point>{
		long x, y;

		Point(long counth, long counts) {
			x = counth;
			y = counts;
		}

		@Override
		public int compareTo(Point p )
		{
			return Long.compare(p.y*1l*x, p.x*1l*y);
		}
	}

	static TreeSet<Long> primeFactors(long N) // O(sqrt(N) / ln sqrt(N))
	{
		TreeSet<Long> factors = new TreeSet<Long>(); // take abs(N) in case of -ve integers
		int idx = 0, p = primes.get(idx);

		while (p * p <= N) {
			while (N % p == 0) {
				factors.add((long) p);
				N /= p;
			}
			if (primes.size() > idx + 1)
				p = primes.get(++idx);
			else
				break;
		}

		if (N != 1) // last prime factor may be > sqrt(N)
			factors.add(N); // for integers whose largest prime factor has a power of 1
		return factors;
	}

	static boolean visited[];

	/**
	 * static int bfs(int s) { Queue<Integer> q = new LinkedList<Integer>();
	 * q.add(s); int count=0; int maxcost=0; int dist[]=new int[n]; dist[s]=0;
	 * while(!q.isEmpty()) {
	 * 
	 * int u = q.remove(); if(dist[u]==k) { break; } for(Pair v: adj[u]) {
	 * maxcost=Math.max(maxcost, v.cost);
	 * 
	 * 
	 * 
	 * if(!visited[v.v]) {
	 * 
	 * visited[v.v]=true; q.add(v.v); dist[v.v]=dist[u]+1; maxcost=Math.max(maxcost,
	 * v.cost); } }
	 * 
	 * } return maxcost; }
	 **/

	static boolean[] vis2;

	static boolean f2 = false;

	static long[][] matMul(long[][] a2, long[][] b, int p, int q, int r) // C(p x r) = A(p x q) x (q x r) -- O(p x q x
																			// r)
	{
		long[][] C = new long[p][r];
		for (int i = 0; i < p; ++i) {
			for (int j = 0; j < r; ++j) {
				for (int k = 0; k < q; ++k) {
					C[i][j] = (C[i][j] + (a2[i][k] % mod * b[k][j] % mod)) % mod;
					C[i][j] %= mod;
				}

			}
		}
		return C;
	}
	public static int[] schuffle(int[] a2) {
		for (int i = 0; i < a2.length; i++) {
			int x =  (int) (Math.random() * a2.length);
			int temp = a2[x];
			a2[x] = a2[i];
			a2[i] = temp;
		}
		return a2;
	}


	static boolean vis[];
	static HashSet<Integer> set = new HashSet<Integer>();

	static long modPow(long ways, long count, long mod) // O(log e)
	{
		ways %= mod;
		long res = 1;
		while (count > 0) {
			if ((count & 1) == 1)
				res = (res * ways) % mod;
			ways = (ways * ways) % mod;
			count >>= 1;
		}
		return res % mod;
	}

	static long gcd(long l, long o) {
		if (o == 0) {
			return l;
		}
		return gcd(o, l % o);
	}

	static int[] isComposite;
	static int[] valid;

	static ArrayList<Integer> primes;
	static ArrayList<Integer> l1;

	
	static TreeSet<Integer> primus = new TreeSet<Integer>();
	
	static void sieveLinear(int N)
	{
		int[] lp = new int[N + 1];								//lp[i] = least prime divisor of i
		for(int i = 2; i <= N; ++i)
		{
			if(lp[i] == 0)
			{
				primus.add(i);
				lp[i] = i;
			}
			int curLP = lp[i];
			for(int p: primus)
				if(p > curLP || p * i > N)
					break;
				else
					lp[p * i] = i;
		}
	}
	

	public static long[] schuffle(long[] a2) {
		for (int i = 0; i < a2.length; i++) {
			int x =  (int) (Math.random() * a2.length);
			long temp = a2[x];
			a2[x] = a2[i];
			a2[i] = temp;
		}
		return a2;
	}

	static class Scanner {
		StringTokenizer st;
		BufferedReader br;

		public Scanner(InputStream system) {
			br = new BufferedReader(new InputStreamReader(system));
		}

		public Scanner(String file) throws Exception {
			br = new BufferedReader(new FileReader(file));
		}

		public String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		public String nextLine() throws IOException {
			return br.readLine();
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		public double nextDouble() throws IOException {
			return Double.parseDouble(next());
		}

		public char nextChar() throws IOException {
			return next().charAt(0);
		}

		public Long nextLong() throws IOException {
			return Long.parseLong(next());
		}

		public boolean ready() throws IOException {
			return br.ready();
		}

		public void waitForInput() throws InterruptedException {
			Thread.sleep(3000);
		}

		public int[] nxtArr(int n) throws IOException {
			int[] ans = new int[n];
			for (int i = 0; i < n; i++)
				ans[i] = nextInt();
			return ans;
		}
		
	}
	public static int[] sortarray(int a[]) {
		schuffle(a);
		Arrays.sort(a);
		return a;
		}
		public static long[] sortarray(long a[]) {
			schuffle(a);
			Arrays.sort(a);
			return a;
		}
		public static int[] readarray(int n) throws IOException {
			int a[]=new int[n];
			for (int i = 0; i < a.length; i++) {
			a[i]=sc.nextInt();
			}
			return a;
		}
		public static long[] readlarray(int n) throws IOException {
			long a[]=new long[n];
			for (int i = 0; i < a.length; i++) {
			a[i]=sc.nextLong();
			}
			return a;
		}
}