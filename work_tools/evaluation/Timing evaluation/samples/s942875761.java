import java.util.Map.Entry;
import java.util.*;
 
//import org.w3c.dom.css.Counter;
 
public class Main {
//public class App {
 
    public static void main(final String[] args){
     //スキャン
     final Scanner s = new Scanner(System.in);
      int N = Integer.parseInt(s.next());
      int X = Integer.parseInt(s.next());
      int T = Integer.parseInt(s.next());
      int ans = N /X;
      if(N%X !=0){ans ++;}
      System.out.println(ans*T);
    }
}
