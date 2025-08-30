import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Main {
	
	public static void main(String[] args) throws IOException {
		Main mainObj = new Main();
		mainObj.solve();
	}

	public void solve() throws IOException {
		FastScanner fs = new FastScanner();
		
		int n = fs.nextInt();
		int m = fs.nextInt();
		
		TreeSet<Integer> set = new TreeSet<>();
		HashMap<Integer, Integer> map = new HashMap<>();
		
		for(int i = 0; i < n; i++) {
			int x = fs.nextInt();
			if(!set.contains(x)) {
				set.add(x);
			}
			
			if(map.containsKey(x)) {
				int num = map.get(x);
				map.put(x, num+1);
			}else {
				map.put(x, 1);
			}
		}
		
		for(int i = 0; i < m; i++) {
			int last = set.last();
			
			int numLast = map.get(last);
			map.put(last, numLast - 1);
			
			if(map.get(last) == 0) {
				set.remove(last);
			}
			
			last /= 2;
			
			if(!set.contains(last)) {
				set.add(last);
			}
			
			if(map.containsKey(last)) {
				int numHalfLast = map.get(last);
				map.put(last, numHalfLast+1);
			}else {
				map.put(last, 1);
			}
		}
		
		long ans = 0;
		for(Integer price : set) {
			ans += (long)price * map.get(price);
//			System.out.println(price);
//			System.out.println(map.get(price));
		}
		
		System.out.println(ans);
	}
	
	public class SegmentTree {
		int reafSize;
		int[] tree;
		TreeFunc treeFunc;
		int notInSectionValue;

		public SegmentTree(int size, TreeFunc treeFunc, int notInSectionValue) {
			reafSize = 1;
			while (reafSize < size) {
				reafSize *= 2;
			}
			tree = new int[reafSize * 2 - 1];
			this.treeFunc = treeFunc;
			this.notInSectionValue = notInSectionValue;
		}

		public void update(int pos, int val) {
			int pointer = reafSize + pos - 1;
			tree[pointer] = val;
			while (pointer > 0) {
				pointer = (pointer - 1) / 2;
				tree[pointer] = treeFunc.calc(tree[pointer * 2 + 1], tree[pointer * 2 + 2]);
			}
		}

		public int query(int a, int b, int k, int l, int r) {

			if (r < 0) {
				r = reafSize;
			}

			if (r <= a || b <= l) {
				return notInSectionValue;
			}
			if (a <= l && r <= b) {
				return tree[k];
			}

			return treeFunc.calc(query(a, b, k * 2 + 1, l, (l + r) / 2), query(a, b, k * 2 + 2, (l + r) / 2, r));
		}
	}

	@FunctionalInterface
	interface TreeFunc {
		public int calc(int left, int right);
	}


	

	public class FastScanner {

		BufferedReader reader;
		private StringTokenizer st;

		public FastScanner() {
			st = null;
			reader = new BufferedReader(new InputStreamReader(System.in));
		}

		public String next() throws IOException {
			if (st == null || !st.hasMoreElements()) {
				st = new StringTokenizer(reader.readLine());
			}
			return st.nextToken();
		}

		public String nextLine() throws IOException {
			st = null;
			String readLine = null;
			readLine = reader.readLine();
			return readLine;
		}

		public int nextInt() throws NumberFormatException, IOException {
			return Integer.parseInt(next());
		}

		public long nextLong() throws NumberFormatException, IOException {
			return Long.parseLong(next());
		}

		public int[] nextIntArr(int n) throws NumberFormatException, IOException {
			int[] retArr = new int[n];
			for (int i = 0; i < n; i++) {
				retArr[i] = nextInt();
			}
			return retArr;
		}

		public long[] nextLongArr(int n) throws NumberFormatException, IOException {
			long[] retArr = new long[n];
			for (int i = 0; i < n; i++) {
				retArr[i] = nextLong();
			}
			return retArr;
		}

		public void close() throws IOException {
			reader.close();
		}
	}


}
