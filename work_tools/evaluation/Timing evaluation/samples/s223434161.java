import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

  public static void main(String[] args) throws IOException {
    
    try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

      int q = Integer.parseInt(br.readLine());
      String[] s = new String[q+q];
      Arrays.fill(s, "");
      List<String> list  = Arrays.asList(s);
      int head = q;
      int tail = q;

      String[] query;
      int op;
      for ( int i=0; i<q; i++ ){
        query = br.readLine().split(" ");
        op  = Integer.parseInt(query[0]);

        switch( op ) {
          case 0 :
            if ( Integer.parseInt(query[1]) == 0 )
              list.set(head--,query[2]);
            else
              list.set(++tail,query[2]);
            break;
          case 1 :
            System.out.println( list.get(head+1 + Integer.parseInt(query[1])) );
            break;
          case 2 :
            if ( Integer.parseInt(query[1]) == 0 )
              head++; // list.set(head++,"");
            else
              tail--; // list.set(tail--,"");
            break;
          default:
        }
      }
    }
  }
}
