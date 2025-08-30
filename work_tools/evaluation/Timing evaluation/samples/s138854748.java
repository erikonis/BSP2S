import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
  
public class Main {
    private static  BufferedReader  br = null;
    private static  double[][]      cd = null;
    private static  int[]           rr = { Integer.MAX_VALUE, Integer.MIN_VALUE };
 
    static {
        br = new BufferedReader(new InputStreamReader(System.in));
    }
  
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int     size = 0;
        Data    data = null;
        DataTbl tbl  = new DataTbl();
         
        while ((data = parseData()) != null) {
            tbl.add(data);
        }
 
        calcDist();
 
        size = tbl.size();
        for (int i = 0; i < size; i++) {
            data = tbl.get(i);
            System.out.println(solve(data));
        }
    }
 
    public static String solve(Data data) {
        String  res = "NA";
        double  min = Double.MAX_VALUE;
        double  tmp = 0.0;
 
        do {
            tmp = (double)data.cr[0]+(double)data.cr[data.cr.length-1];
            for (int i = 0; i < data.cr.length-1; i++) {
                tmp += cd[data.cr[i]-rr[0]][data.cr[i+1]-rr[0]];
            }
            min = Math.min(min, tmp);
 
            if (min <= data.wi) {
                res = "OK";
                break;
            }
        } while(next_perm(data.cr));
 
        return res;
    }
 
    static boolean next_perm(int[] cr) {
        int     i;
        int     j;
        int     t;
        int     l = cr.length;
        boolean r = false;
         
        if (l > 0) {
            for(i = l - 1; i > 0 && cr[i-1] >= cr[i]; i--);
 
            if (i > 0) {
                for(j = l - 1; j > i && cr[i-1] >= cr[j]; j--);
 
                // swap(p,j,i-1);
                t = cr[j];
                cr[j] = cr[i-1];
                cr[i-1] = t;
 
                for(j=l-1; i < j; i++, j--){
                    // swap(p,i,j);
                    t = cr[i];
                    cr[i] = cr[j];
                    cr[j] = t;
                }
 
                r = true;
            }
        }
 
        return r;
    }
 
    public static void calcDist() {
        int sz = rr[1]-rr[0];
 
        cd = new double[sz+1][sz+1];
        for (int i = 0; i <= sz; i++) {
            for (int j = i; j <= sz; j++) {
                double  d = 0.0;
                if (i == j) {
                    d = (double)((rr[0]+i)*2);
                } else {
                    d = Math.sqrt(Math.pow((double)(rr[0]*2+j+i), 2.0)-Math.pow((double)(j-i), 2.0));
                }
                cd[i][j] = d;
                cd[j][i] = d;
            }
        }
    }
 
    private static Data parseData() {
        Data    data  = null;
        String  stdin  = null;
 
        if ((stdin = parseStdin()) != null) {
            String[]    lines = stdin.split(" ");
 
            data = new Data(lines.length-1);
            for(int i = 0; i < lines.length; i++) {
                if (i == 0) {
                    data.wi = Integer.parseInt(lines[i]);
                } else {
                    data.cr[i-1] = Integer.parseInt(lines[i]);
                    rr[0] = Math.min(rr[0], data.cr[i-1]);
                    rr[1] = Math.max(rr[1], data.cr[i-1]);
                }
            }
            Arrays.sort(data.cr);
        }
 
        return data;
    }
 
    private static String parseStdin() {
        String  stdin = null;
 
        try {
            String  tmp = br.readLine();
            if (tmp != null) {
                if (!tmp.isEmpty()) stdin = tmp;
            }
        }
        catch (IOException e) {}
 
        return stdin;
    }
}
 
class DataTbl extends ArrayList<Data> {
}
 
class Data {
    int         wi = 0;
    int[]       cr = null;
 
    Data (int cc) {
        cr = new int[cc];
    }
}