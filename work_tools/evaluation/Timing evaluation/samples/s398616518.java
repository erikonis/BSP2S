import java.util.*;

public class Main {
	static int mod = 1000000007;
  static int size = 200000;
	static long[] fac = new long[size];
	static long[] finv = new long[size];
	static long[] inv = new long[size];
  static int INF = Integer.MAX_VALUE;

 	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int[] a = new int[n+1];
		for(int i = 0; i < n; i++){
			a[i] = scanner.nextInt();
		}
		//i番目までの和がjとなる場合の数
		long[][] dp = new long[n+1][21];
		dp[0][a[0]] = 1;
		for(int i = 1; i < n-1; i++){
			for(int j = 0; j <= 20; j++){
				if(j-a[i] >= 0){
					dp[i][j-a[i]] += dp[i-1][j];
				}
				if(j+a[i] <= 20){
					dp[i][j+a[i]] += dp[i-1][j];
				}
			}
		}
		System.out.println(dp[n-2][a[n-1]]);
	}
  static class Pair implements Comparable<Pair>{
    int x, y;
    Pair(int a, int b){
        x = a;
        y = b;
    }
    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;
        Pair p = (Pair) o;
        return x == p.x && y == p.y;
    }
    @Override
    public int compareTo(Pair p){
        //return x == p.x ? y - p.y : x - p.x; //xで昇順にソート
        //return (x == p.x ? y - p.y : x - p.x) * -1; //xで降順にソート
        //return y == p.y ? x - p.x : y - p.y;//yで昇順にソート
        return (y == p.y ? x - p.x : y - p.y)*-1;//yで降順にソート
    }
  }
  //繰り返し二乗法
  public static long pow(long x, long n){
    long ans = 1;
    while(n > 0){
      if((n & 1) == 1){
        ans = ans * x;
        ans %= mod;
      }
      x = x * x % mod;
      n >>= 1;
    }
    return ans;
  }

  //fac, inv, finvテーブルの初期化、これ使う場合はinitComb()で初期化必要
	public static  void initComb(){
		fac[0] = finv[0] = inv[0] = fac[1] = finv[1] = inv[1] = 1;
		for (int i = 2; i < size; ++i) {
			fac[i] = fac[i - 1] * i % mod;
			inv[i] = mod - (mod / i) * inv[(int) (mod % i)] % mod;
			finv[i] = finv[i - 1] * inv[i] % mod;
		}
	}

	//nCk % mod
	public static long comb(int n, int k){
		return fac[n] * finv[k] % mod * finv[n - k] % mod;
	}

	//n! % mod
	public static long fact(int n){
		return fac[n];
	}

	//(n!)^-1 with % mod
	public static long finv(int n){
		return finv[n];
	}

  static class UnionFind {
    int[] parent;
    public UnionFind(int size) {
      parent = new int[size];
      Arrays.fill(parent, -1);
    }
    public boolean unite(int x, int y) {
      x = root(x);
      y = root(y);
      if (x != y) {
        if (parent[y] < parent[x]) {
          int tmp = y;
          y = x;
          x = tmp;
        }
        parent[x] += parent[y];
        parent[y] = x;
        return true;
      }
      return false;
    }
    public boolean same(int x, int y) {
      return root(x) == root(y);
    }
    public int root(int x) {
      return parent[x] < 0 ? x : (parent[x] = root(parent[x]));
    }
    public int size(int x) {
      return -parent[root(x)];
    }
  }
	public static int upperBound(long[] array, long value) {
			 int low = 0;
			 int high = array.length;
			 int mid;
			 while( low < high ) {
					 mid = ((high - low) >>> 1) + low; // (high + low) / 2
					 if( array[mid] <= value ) {
							 low = mid + 1;
					 } else {
							 high = mid;
					 }
			 }
			 return low;
	 }
  //n,mの最大公約数
  public static int gcd(int n, int m){
    if(m > n) return gcd(m,n);
    if(m == 0) return n;
    return gcd(m, n%m);
  }
}

