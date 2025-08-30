
import java.awt.geom.Point2D;
import java.io.*;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeMap;



public class Main {

	static PrintWriter out = new PrintWriter(System.out);
	static FastScanner sc = new FastScanner();
	static Scanner stdIn = new Scanner(System.in);
	static int INF = 2 << 26;
	static char[][] map;
	public static void main(String[] args) {
		IN:while(true) {
			map  = new char[3][];
			String[] in = new String[3];
			for(int i = 0; i < 3; i++) {
				in[i] = sc.next();
				if(i == 0 && in[i].equals("0")) break IN;
				map[i] = in[i].toCharArray();
			}
			ansC = 'c';
			check(0,0,1,1);
			check(0,2,1,-1);
			for(int i = 0; i < 3; i++) {
				check(i,0,0,1);
				check(0,i,1,0);
			}
			
			if(ansC == 'c') {
				out.println("NA");
			}
			else {
				out.println(ansC);
			}
			
			
			
			
			
			
			
		}
		out.flush();
	}
	static char ansC;
	public static void  check(int y, int x, int vy, int vx) {
		char F = map[y][x];
		if(F == '+') return ;
		for(int i = 1; i < 3; i++) {
			if(F != map[y + vy * i][x + vx * i]) return ;
		}
		ansC = F;
	}
}

//------------------------------//
//-----------//
class FastScanner {
    private final InputStream in = System.in;
    private final byte[] buffer = new byte[1024];
    private int ptr = 0;
    private int buflen = 0;
    private boolean hasNextByte() {
        if (ptr < buflen) {
            return true;
        }else{
            ptr = 0;
            try {
                buflen = in.read(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (buflen <= 0) {
                return false;
            }
        }
        return true;
    }
    private int readByte() { if (hasNextByte()) return buffer[ptr++]; else return -1;}
    private static boolean isPrintableChar(int c) { return 33 <= c && c <= 126;}
    private void skipUnprintable() { while(hasNextByte() && !isPrintableChar(buffer[ptr])) ptr++;}
    public boolean hasNext() { skipUnprintable(); return hasNextByte();}
    public String next() {
        if (!hasNext()) throw new NoSuchElementException();
        StringBuilder sb = new StringBuilder();
        int b = readByte();
        while(isPrintableChar(b)) {
            sb.appendCodePoint(b);
            b = readByte();
        }
        return sb.toString();
    }
    public long nextLong() {
        if (!hasNext()) throw new NoSuchElementException();
        long n = 0;
        boolean minus = false;
        int b = readByte();
        if (b == '-') {
            minus = true;
            b = readByte();
        }
        if (b < '0' || '9' < b) {
            throw new NumberFormatException();
        }
        while(true){
            if ('0' <= b && b <= '9') {
                n *= 10;
                n += b - '0';
            }else if(b == -1 || !isPrintableChar(b)){
                return minus ? -n : n;
            }else{
                throw new NumberFormatException();
            }
            b = readByte();
        }
    }
    
    public int nextInt() {
    	 if (!hasNext()) throw new NoSuchElementException();
         int n = 0;
         boolean minus = false;
         int b = readByte();
         if (b == '-') {
             minus = true;
             b = readByte();
         }
         if (b < '0' || '9' < b) {
             throw new NumberFormatException();
         }
         while(true){
             if ('0' <= b && b <= '9') {
                 n *= 10;
                 n += b - '0';
             }else if(b == -1 || !isPrintableChar(b)){
                 return minus ? -n : n;
             }else{
                 throw new NumberFormatException();
             }
             b = readByte();
         }
    }
    
    public double nextDouble() {
    	return Double.parseDouble(next());
    }
    

}