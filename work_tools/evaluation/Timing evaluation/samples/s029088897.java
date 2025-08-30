import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

public class Main {
	static final long MOD=1000000007;//998244353;
	static long ans;
	public static void main(String[] args) {
		PrintWriter out = new PrintWriter(System.out);
		InputReader sc=new InputReader(System.in);
		int N=sc.nextInt();
		int M=sc.nextInt();
		UnionFindTree unionFindTree=new UnionFindTree(N);
		for (int i = 0; i < M; i++) {
			int L=sc.nextInt()-1;
			int R=sc.nextInt()-1;
			int D=sc.nextInt();
			if (unionFindTree.same(L, R)&&unionFindTree.diff(R, L)!=D) {
				System.out.println("No");
				return;
			}
			unionFindTree.union(R, L, D);
		}
		System.out.println("Yes");
 	}
	static class UnionFindTree{
    	int[] par;
    	int[] rank;
    	int[] size;
    	int[] diff_weight;
    	int arraysize;
    	public UnionFindTree(int n) {
    		this.par=new int[n];
    		this.rank=new int[n];
    		this.size=new int[n];
    		this.diff_weight=new int[n];
    		arraysize=n;
    		for (int i = 0; i < arraysize; i++) {
				set(i);
			}
    	}
    	public void set(int i) {
    		par[i]=i;
    		rank[i]=0;
    		size[i]=1;
    		diff_weight[i]=0;
    	}
    	public void union(int x,int y,int w) {
    		w += weight(x); 
    		w -= weight(y);
    		int rootx=find(x);
    		int rooty=find(y);
    		if (rootx==rooty) {
				return;
			}
    		if (rank[rootx]>rank[rooty]) {
				par[rooty]=rootx;
				diff_weight[rooty] = w;
				size[rootx]+=size[rooty];
			}
    		else if (rank[rootx]<rank[rooty]) {
				par[rootx]=rooty;
				w=-w;
				diff_weight[rootx] = w;
				size[rooty]+=size[rootx];
			}
    		else {
    			par[rooty]=rootx;
    			diff_weight[rooty] = w;
    			rank[rootx]++;
				size[rootx]+=size[rooty];
			}
    	}
    	public int find(int x) {
    		if(par[x] == x) return x;
    		int r = find(par[x]);
    		diff_weight[x] += diff_weight[par[x]];
    		return par[x] = r;
    	}
    	public int weight(int x) {
    		find(x);
    		return diff_weight[x];
    	}
    	public int size(int i) {
            return size[find(i)];
        }
    	public int diff(int x, int y) {
    		return weight(y) - weight(x);
    	}
    	public boolean same(int x, int y) {
            return find(x) == find(y);
        }
    }
	static class InputReader { 
		private InputStream in;
		private byte[] buffer = new byte[1024];
		private int curbuf;
		private int lenbuf;
		public InputReader(InputStream in) {
			this.in = in;
			this.curbuf = this.lenbuf = 0;
		}
 
		public boolean hasNextByte() {
			if (curbuf >= lenbuf) {
				curbuf = 0;
				try {
					lenbuf = in.read(buffer);
				} catch (IOException e) {
					throw new InputMismatchException();
				}
				if (lenbuf <= 0)
					return false;
			}
			return true;
		}
 
		private int readByte() {
			if (hasNextByte())
				return buffer[curbuf++];
			else
				return -1;
		}
 
		private boolean isSpaceChar(int c) {
			return !(c >= 33 && c <= 126);
		}
 
		private void skip() {
			while (hasNextByte() && isSpaceChar(buffer[curbuf]))
				curbuf++;
		}
 
		public boolean hasNext() {
			skip();
			return hasNextByte();
		}
 
		public String next() {
			if (!hasNext())
				throw new NoSuchElementException();
			StringBuilder sb = new StringBuilder();
			int b = readByte();
			while (!isSpaceChar(b)) {
				sb.appendCodePoint(b);
				b = readByte();
			}
			return sb.toString();
		}
 
		public int nextInt() {
			if (!hasNext())
				throw new NoSuchElementException();
			int c = readByte();
			while (isSpaceChar(c))
				c = readByte();
			boolean minus = false;
			if (c == '-') {
				minus = true;
				c = readByte();
			}
			int res = 0;
			do {
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res = res * 10 + c - '0';
				c = readByte();
			} while (!isSpaceChar(c));
			return (minus) ? -res : res;
		}
 
		public long nextLong() {
			if (!hasNext())
				throw new NoSuchElementException();
			int c = readByte();
			while (isSpaceChar(c))
				c = readByte();
			boolean minus = false;
			if (c == '-') {
				minus = true;
				c = readByte();
			}
			long res = 0;
			do {
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res = res * 10 + c - '0';
				c = readByte();
			} while (!isSpaceChar(c));
			return (minus) ? -res : res;
		}
 
		public double nextDouble() {
			return Double.parseDouble(next());
		}
 
		public int[] nextIntArray(int n) {
			int[] a = new int[n];
			for (int i = 0; i < n; i++)
				a[i] = nextInt();
			return a;
		}
 
		public long[] nextLongArray(int n) {
			long[] a = new long[n];
			for (int i = 0; i < n; i++)
				a[i] = nextLong();
			return a;
		}
 
		public char[][] nextCharMap(int n, int m) {
			char[][] map = new char[n][m];
			for (int i = 0; i < n; i++)
				map[i] = next().toCharArray();
			return map;
		}
	}
	}
