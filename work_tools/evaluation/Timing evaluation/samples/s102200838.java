import java.util.Arrays;
import java.util.Scanner;

public class Main {
  Scanner sc;

  void run() {
    for ( ;; ) {
      int N = ni();
      int M = ni();

      if ( ( N | M ) == 0 ) {
        break;
      }

      int[][] imos = new int[M + 1][1261];
      int r = ni();
      for ( int i = 0; i < r; ++i ) {
        int t = ni();
        int n = ni();
        int m = ni();
        int s = ni();

        // テ・ツ青古」ツ?蕨テ」ツ?ァテ・ツ青古ヲツ卍づ・ツ按サテ」ツ?ォテ」ツつ「テ」ツつッテ」ツつサテ」ツつケテ」ツ?凖」ツつ凝」ツ?禿」ツ?ィテ」ツ?ッテ」ツ?ェテ」ツ??」ツ?妥」ツ?ゥテ」ツ??
        // テゥツ?陛」ツ??テ」ツ?ェテ」ツつ嘉」ツ?づ」ツつ甘」ツ?暗」ツつ凝」ツ?づ」ツ?ェテ」ツ?ョテ」ツ?ァ"+="テ」ツ?ォテ」ツ?凖」ツつ凝・ツソツ?ィツヲツ?」ツ?古」ツ?づ」ツつ凝」ツ??
        // テ」ツ?敕」ツつづ」ツ?敕」ツつづィツォツ姪」ツ?ィテ」ツ?療」ツ?ヲテ」ツ?（mosテ」ツ?凖」ツつ凝」ツ?ェテ」ツつ?+="テ」ツ?療」ツつ暗」ツ??」ツ??
        imos[ m ][ t ] += s * 2 - 1;
      }

      int q = ni();
      for ( int i = 0; i < q; ++i ) {
        int ts = ni();
        int te = ni();
        int m = ni();

        int sum = 0;
        int v = 0;
        for ( int t = 540; t <= 1260; ++t ) {
          v += imos[ m ][ t ];
          if ( v > 0 && ts <= t && t < te ) {
            ++sum;
          }
        }

        System.out.println( sum );
      }
    }
  }

  Main() {
    sc = new Scanner( System.in );
  }

  int ni() {
    return sc.nextInt();
  }

  public static void main(String[] args) {
    new Main().run();
  }

  void debug(Object... os) {
    System.err.println( Arrays.deepToString( os ) );
  }
}