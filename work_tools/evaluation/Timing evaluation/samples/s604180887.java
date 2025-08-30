import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] ia;

        while(true){
            ia = in.readLine().split(" ");
            int a = Integer.parseInt(ia[0]);
            int L = Integer.parseInt(ia[1]);
            if(a == 0 && L == 0){
                break;
            }
            int now = a;
            TreeMap<Integer, Integer>tm = new TreeMap<Integer, Integer>();
            tm.put(a,0);
            for(int i = 1;;++i){
                char[] tmp = (new Integer(a)).toString().toCharArray();
                Arrays.sort(tmp);
                StringBuffer sb = (new StringBuffer()).append(tmp);
                int small = Integer.parseInt(sb.toString());
                sb = sb.reverse();
                while(sb.length() < L){
                    sb.append('0');
                }
                int large = Integer.parseInt(sb.toString());
                Integer old;
                if((old = tm.get(large-small)) != null){
                    System.out.println("" + old + " " + (large-small) + " " + (i - old));
                    break;
                } else {
                    tm.put(large-small, i);
                    a = large-small;
                }
            }
        }
    }
}