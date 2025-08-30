import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {
	
	public static void main(String[] args) throws IOException {
		try(Scanner sc = new Scanner(System.in)){
			final long A = sc.nextLong();
			final long B = sc.nextLong();
			final long C = sc.nextLong();
			
			long curr_A = A, curr_B = B, curr_C = C;
			long cnt = 0;
			while(true){
				if(curr_A % 2 == 1 || curr_B % 2 == 1 || curr_C % 2 == 1){
					System.out.println(cnt);
					return;
				}
				
				final long next_A = curr_B / 2 + curr_C / 2;
				final long next_B = curr_A / 2 + curr_C / 2;
				final long next_C = curr_A / 2 + curr_B / 2;
				
				curr_A = next_A;
				curr_B = next_B;
				curr_C = next_C;
				cnt++;
				if(curr_A == A && curr_B == B && curr_C == C){
					System.out.println(-1);
					break;
				}
			}
		}
	}
	
	public static class Scanner implements Closeable {
		private BufferedReader br;
		private StringTokenizer tok;
 
		public Scanner(InputStream is) throws IOException {
			br = new BufferedReader(new InputStreamReader(is));
		}
 
		private void getLine() throws IOException {
			while (!hasNext()) {
				tok = new StringTokenizer(br.readLine());
			}
		}
 
		private boolean hasNext() {
			return tok != null && tok.hasMoreTokens();
		}
 
		public String next() throws IOException {
			getLine();
			return tok.nextToken();
		}
 
		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}
 
		public long nextLong() throws IOException {
			return Long.parseLong(next());
		}
 
		public double nextDouble() throws IOException {
			return Double.parseDouble(next());
		}
		
		public int[] nextIntArray(int n) throws IOException {
			final int[] ret = new int[n];
			for(int i = 0; i < n; i++){
				ret[i] = this.nextInt();
			}
			return ret;
		}
		
		public long[] nextLongArray(int n) throws IOException {
			final long[] ret = new long[n];
			for(int i = 0; i < n; i++){
				ret[i] = this.nextLong();
			}
			return ret;
		}
 
		public void close() throws IOException {
			br.close();
		}
	}
}