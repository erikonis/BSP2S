import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.io.*;
//import java.util.stream.IntStream;
import java.nio.charset.Charset;


public class Main {

    static int INFTY = Integer.MAX_VALUE;
    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);
      //  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


     //   try{

            int n = sc.nextInt();
            int[] a = new int[n];
            // 長さiの最長増加部分列の最後尾の値、(同じ長さの場合は最小値)
            int[] l = new int[n+1];
            Arrays.fill(l, -1);
            int length = 0;

            for (int i = 0; i < n; i++) {
                a[i] = sc.nextInt();
            }

            for (int i = 0; i < n; i++) {
                //最大値より大きい場合は入れ替えだけ
                if(a[i]>l[length]){
                    l[length + 1] = a[i];
                    length++;
                    continue;
                }

                int index = Arrays.binarySearch(l, 0, length, a[i]);
                // 見つからなかった場合の戻り値はマイナスの変な数になっている
                if(index < 0){
                    l[-index - 1] = a[i];
                } else {
                    l[index] = a[i];
                }
                
            }
            
            System.out.println(length);

    }

}


