import java.util.*;
import java.io.*;
 
 
public class Main {
    public static void main(String[] args) throws Exception {
        FastScanner sc = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);
        
        //181
        
        int[] tetranum = new int[180];
        int odd_n = 0;
        
    	for(int i=0; i<180; i++){
    	    tetranum[i] = i+1;
    	    tetranum[i] *= (i+2);
    	    tetranum[i] *= (i+3);
    	    tetranum[i] /= 6;
    	    if(tetranum[i]%2 == 1) odd_n++;
    	}
        
        int[] odd_tetra = new int[odd_n];
        int moving = 0;
        
    	for(int i=0; i<180; i++){
    	    if(tetranum[i]%2==1){
    	        odd_tetra[moving] = tetranum[i];
    	        moving++;
    	    }
    	}
    	
        int[] dp = new int[1000010];     //iを表す時の個数のmin
        int[] oddp = new int[1000010];     //iを表す時の個数のmin
        
        dp[0]=0;
        for(int j=1; j<1000010; j++){
            dp[j] = -1;  //don't know
        }
        
        oddp[0]=0;
        for(int j=1; j<1000010; j++){
            oddp[j] = -1;  //don't know
        }
        
        boolean all_fin=false;
        moving=0;
        while(!all_fin){
            all_fin=true;
            for(int j=1; j<1000010; j++){
                if(dp[j] < 0){
    	            for(int i=0; i<180; i++){
    	                if(tetranum[i] <= j){
    	                    if(dp[j-tetranum[i]] == moving){
    	                        dp[j] = moving+1;
    	                    }
    	                }
    	            }
    	            all_fin=false;
                }
            }
            moving++;
        }
        
        all_fin=false;
        moving=0;
        while(!all_fin){
            all_fin=true;
            for(int j=1; j<1000010; j++){
                if(oddp[j] < 0){
    	            for(int i=0; i<odd_n; i++){
    	                if(odd_tetra[i] <= j){
    	                    if(oddp[j-odd_tetra[i]] == moving){
    	                        oddp[j] = moving+1;
    	                    }
    	                }
    	            }
    	            all_fin=false;
                }
            }
            moving++;
        }
        
        int a=0;
        while(true){
            a = sc.nextInt();
            if(a==0) break;
            out.println(dp[a]+" "+oddp[a]);
        }
        
        
        out.flush();
    }
    
    private static int minExcept(int[] dt, int ex){
        int min=Integer.MAX_VALUE;
        
    	for(int i=0; i<dt.length; i++){
    	    if((i != ex) && (dt[i] < min)) min = dt[i];
    	}
    	return min;
    }
    
    private static int abs(int a){
        if(a>=0) return a;
        else return -a;
    }
    private static int min(int a, int b){
        if(a>b) return b;
        else return a;
    }
    private static int min2(int... ins){
        int min = ins[0];
        
    	for(int i=1; i<ins.length; i++){
    	    if(ins[i] < min) min = ins[i];
    	}
    	return min;
    }
    private static int max(int a, int b){
        if(a>b) return a;
        else return b;
    }
    private static int max2(int... ins){
        int max = ins[0];
        
    	for(int i=1; i<ins.length; i++){
    	    if(ins[i] > max) max = ins[i];
    	}
    	return max;
    }
    
    
    static void show_i2d_array(long[][] dt){
        PrintWriter out = new PrintWriter(System.out);
    	for(int i=0; i<dt[0].length; i++){
    		for(int j=0; j<dt.length; j++){
    		    out.print(dt[j][i]+",");
    		}
    		out.println("<-city="+i);
    	}
    	out.flush();
    }
    
    public static void combSort(int[][] origin, int ln){
        int h=ln*10/13, tmp=0;
        boolean fin=true;
        while((h>1)||(!fin)){
            fin=true;
            for(int i=0; i < ln-h; i++){
                if(origin[i][0] > origin[i+h][0]){
                    tmp=origin[i][0];
                    origin[i][0]=origin[i+h][0];
                    origin[i+h][0]=tmp;
                    tmp=origin[i][1];
                    origin[i][1]=origin[i+h][1];
                    origin[i+h][1]=tmp;
                    fin=false;
                }
            }
            h *=10;
            h/=13;
        }
    }
}
 
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
    public boolean hasNext() { while(hasNextByte() && !isPrintableChar(buffer[ptr])) ptr++; return hasNextByte();}
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
        long nl = nextLong();
        if (nl < Integer.MIN_VALUE || nl > Integer.MAX_VALUE) throw new NumberFormatException();
        return (int) nl;
    }
    public double nextDouble() { return Double.parseDouble(next());}
}
