import java.util.*;
import java.io.*;

public class Main {
	private static void solve(){
		//Implement solution here.
		int n = ni();
		int tlim = ni();
		int minc = Integer.MAX_VALUE;
		for(int i = 0; i < n ; i++) {
			int c = ni();
			int t = ni();
			if(t > tlim) {
				continue;
			}else {
				minc = Math.min(minc, c);
			}
		}
		if(minc == Integer.MAX_VALUE) {
			System.out.println("TLE");
		}else {
			System.out.println(minc);
		}
	}
	
	private static int dfs(int dig, int cur, int max) {
		int result = 0;	
		if(dig >= 3) {
			if(dig <= Integer.toString(max).length()) {
				if(cur <= max) {
					if(is753(cur))result++;
				} else {
					return 0;
				}
			} else {
				return 0;
			}
		}	
		dig++;
		result += dfs(dig, cur * 10 + 3, max) + dfs(dig, cur * 10 + 5, max) + dfs(dig, cur * 10 + 7, max);
		return result;
	}

	private static boolean is753(int in) {
		boolean[] flags = new boolean[3];
		char[] inca = Integer.toString(in).toCharArray();
		for(char c:inca) {
			if(c == '3')flags[0] = true;
			if(c == '5')flags[1] = true;
			if(c == '7')flags[2] = true;
		}
		
		return flags[0] && flags[1] && flags[2];
	}
	//Switch input source (stdin/file)
	private static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args){
		String debugDataPath = System.getenv("DD_PATH");        
		if(debugDataPath != null){
			try{
				scanner = new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream(new File(debugDataPath)))));
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		}
		solve();
	}

	//Input read utility
	private static StringTokenizer tokenizer = null;
	public static String next() {
		while (tokenizer == null || !tokenizer.hasMoreTokens()) {
			try {
				tokenizer = new StringTokenizer(scanner.nextLine());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return tokenizer.nextToken();
	}
	//Get next single int
	private static int ni() {
		return Integer.parseInt(next());
	}
	//Get next single long
	private static long nl() {
		return Long.parseLong(next());
	}
	//Get next single double
	private static double nd() {
		return Double.parseDouble(next());
	}
	//Get next int array from one line
	private static int[] na(int n) {
		int[] a = new int[n];
		for (int i = 0; i < n; i++)
			a[i] = ni();
		return a;
	}
	//Get next char array from one line
	private static char[] ns() {
		return next().toCharArray();
	}
	//Get next long array from one line
	private static long[] nal(int n) {
		long[] a = new long[n];
		for (int i = 0; i < n; i++)
			a[i] = nl();
		return a;
	}
}