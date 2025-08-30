import java.io.*;
import java.util.*;
import java.lang.ArrayIndexOutOfBoundsException;
import java.math.BigInteger;

/**
 * @author yoshikyoto
 */
class Main {
	public static void main(String[] args) throws Exception{
		Scanner sc = new Scanner(new InputStreamReader(System.in));
		while(true){
			int n = sc.nextInt();
			int w = sc.nextInt();
			int h = sc.nextInt();
			if(n + w + h == 0) break;

			ArrayList<Cake> arr = new ArrayList<Cake>();
			arr.add(new Cake(w, h));

			for(int i = 0; i < n; i++){
				int p = sc.nextInt() - 1;
				int s = sc.nextInt();

				Cake target_cake = arr.get(p);
				// System.out.println(target_cake.size());
				arr.remove(p);
				Cake second_cake = target_cake.cut(s);
				arr.add(target_cake);
				arr.add(second_cake);
			}

			Collections.sort(arr, new CakeComp());

			StringBuffer buf = new StringBuffer();
			for (int i = 0; i < arr.size(); i++) {
				buf.append(arr.get(i).size());
				buf.append(" ");
			}
			System.out.println(buf.toString().trim());
		}
		sc.close();
	}
}

class Cake {
	int w, h;
	
	Cake(int w, int h){
		this.w = w;
		this.h = h;
	}
	
	int size(){
		return w * h;
	}
	
	Cake cut(int s){
		int half = w + h;
		s = s % half;
		// System.out.println("cut " + s);
		
		// 縦に切る
		Cake cake1 = new Cake(s, h);
		Cake cake2 = new Cake(w-s, h);
		if(s > w){
			// System.out.println("yoko cut");
			// 横に切る
			s -= w;
			cake1 = new Cake(w, s);
			cake2 = new Cake(w, h-s);
		}
		
		if(cake1.size() < cake2.size()){
			this.h = cake1.h;
			this.w = cake1.w;
			return cake2;
		}else{
			this.h = cake2.h;
			this.w = cake2.w;
			return  cake1;
		}
	}
}

class CakeComp implements Comparator<Cake> {
	@Override
	public int compare(Cake a, Cake b) {
		return a.size() - b.size();
	}
}


class BinaryIndexedTree{
    int n;
    int[] bit;
    BinaryIndexedTree(int n){
        this.n = n;
        bit = new int[n+1];
    }
    int sum(int i){
        int sum = 0;
        while(i > 0){
            sum += bit[i];
            i -= i & -i;
        }
        return sum;
    }
    void add(int i, int v){
        while(i <= n){
            bit[i] += v;
            i += i & -i;
        }
    }
}


// --- ここから下はライブラリ ----------
/**
 * MyUtil
 * @author yoshikyoto
 */
class MyUtil {
	public static int toInt(boolean[] a){
		int pow = 1, ret = 0, l = a.length;
		for(int i = 0; i < l; i++){
			if(a[i]) ret += pow;
			pow *= 2;
		}
		return ret;
	}
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static int ins[];
	public static int[] readIntMap() throws IOException{return parseInt(readLine().split(" "));}
	public static int readIntMap(int i) throws Exception{
		if(i == 0) ins = readIntMap();
		return ins[i];
	}
	public static int[][] readIntMap(int n, int m) throws IOException{
		int[][] ret = new int[n][];
		for(int i = 0; i < n; i++) ret[i] = readIntMap();
		return ret;
	}
	public static int[] readIntToMap(int n) throws IOException{
		int[] ret = new int[n];
		for(int i = 0; i < n; i++) ret[i] = readInt();
		return ret;
	}
	public static int[] readNoDistIntMap() throws IOException{
		String[] strs = readLine().split("");
		int l = strs.length;
		int[] ret = new int[l-1];
		for(int i = 1; i < l; i++)
			ret[i-1] = parseInt(strs[i]);
		return ret;
	}
	public static String readLine() throws IOException{return br.readLine();}
	public static int readInt() throws IOException{return Integer.parseInt(br.readLine());}
	public static int[] parseInt(String[] arr){
		int[] res = new int[arr.length];
		for(int i = 0; i < arr.length; i++)res[i] = Integer.parseInt(arr[i]);
		return res;
	}
	public static double[] parseDouble(String[] arr){
		double[] res = new double[arr.length];
		for(int i = 0; i < arr.length; i++)res[i] = Double.parseDouble(arr[i]);
		return res;
	}
	public static boolean[] parseBool(String[] arr){
		int[] t = parseInt(arr);
		boolean[] res = new boolean[t.length];
		for(int i = 0; i < t.length; i++){
			if(t[i] == 1){res[i] = true;
			}else{res[i] = false;}
		}
		return res;
	}
	public static int parseInt(Object o){
		return Integer.parseInt(o.toString());
	}
}