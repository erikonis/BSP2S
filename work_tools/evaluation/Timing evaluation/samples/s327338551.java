import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeSet;

public class Main {

	public static void main(String[] args) {
		IO sc = new IO();
		int n = sc.nextInt();
		int r = sc.nextInt();
		int l = sc.nextInt();
		int[] time = new int[n];
		Team[] team = new Team[n];
		for(int i=0;i<n;i++) {
			team[i] = new Team(i,0);
		}
		TreeSet<Team> ts = new TreeSet<Team>();
		int winner = 0;
		int tbef = 0;
		for(int i=0;i<=r;i++) {
			int d,t,x;
			if (i < r) {
				d = sc.nextInt()-1;
				t = sc.nextInt();
				x = sc.nextInt();
			}else{
				d = 0;
				t = l;
				x = 0;
			}
			ts.remove(team[d]);
			team[d].score += x;
			ts.add(team[d]);
			time[winner] += t - tbef;
			tbef = t;
			winner = ts.first().id;
		}
		int ans = 0;
		int max = 0;
		for(int i=0;i<n;i++) {
			if (time[i] > max) {
				max = time[i];
				ans = i;
			}
		}
		System.out.println(ans+1);
	}

	static class Team implements Comparable<Team>{
		int id, score;
		public Team(int id,int score) {
			this.id = id;
			this.score = score;
		}
		public boolean equals(Object o) {
			if (o instanceof Team) {
				Team t = (Team) o;
				return this.id == t.id;
			}
			return super.equals(o);
		}
		public int compareTo(Team o) {
			if (this.score != o.score) {
				return o.score-this.score;
			}
			return this.id-o.id;
		}
		public String toString() {
			return "[id:" + this.id + " ,score:" + this.score + "]";
		}
	}
}

class IO {
	BufferedReader bi = new BufferedReader(new InputStreamReader(System.in));
	StringBuilder out = new StringBuilder();
	int index = 0;
	String bfl = null;
	String[] bf = new String[0];
	private boolean read() {
		try {
			bfl = bi.readLine();
			if (bfl == null) {
				return false;
			}
			bf = bfl.split("\\s");
			index = 0;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	public boolean hasNext() { return index < bf.length ? true : read(); }
	public boolean hasNextLine() { return read(); }
	public String next() { return hasNext() ? bf[index++] : null; }
	public String nextLine() { if (hasNextLine()) {index = bf.length; return bfl; }else return null; }
	public int nextInt() { return Integer.parseInt(next()); }
	public long nextLong() { return Long.parseLong(next()); }
	public double nextDouble() { return Double.parseDouble(next()); };
	public char nextChar() { return next().charAt(0); }
	public void println(long x) { out.append(x); out.append("\n"); }
	public void println(double x) { out.append(x); out.append("\n"); }
	public void println(String s) { out.append(s); out.append("\n"); }
	public void println() { out.append("\n"); }
	public void print(long x) { out.append(x); }
	public void print(double x) { out.append(x); }
	public void print(String s) { out.append(s); }
	public void print(char c) {out.append(c);}
	public void flush() {System.out.print(out); out = new StringBuilder(); }
	public int[] arrayInt(int n) {
		int[] a = new int[n];
		for(int i=0;i<n;i++) {
			a[i] = nextInt();
		}
		return a;
	}
	public long[] arrayLong(int n) {
		long[] a = new long[n];
		for(int i=0;i<n;i++) {
			a[i] = nextLong();
		}
		return a;
	}
	public double[] arrayDouble(int n) {
		double[] a = new double[n];
		for(int i=0;i<n;i++) {
			a[i] = nextDouble();
		}
		return a;
	}
}