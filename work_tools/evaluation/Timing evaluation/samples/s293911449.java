import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {

    //グローバル変数を定義
    char[][] array;
    int H;
    int W;

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        H = sc.nextInt();
        W = sc.nextInt();
        array = new char[H][W];
        boolean ans = true;

        for(int i=0; i<H; i++){
            char[] newArray = sc.next().toCharArray();
            for(int j=0; j<W; j++){
                array[i][j] = newArray[j];
            }
        }

        loop: for(int i=0; i<H; i++){
            for(int j=0; j<W; j++){
                char s = array[i][j];
                if(s=='#'){
                    if(!checkArray(i,j)){
                        ans = false;
                        break loop;
                    }
                }
            }
        }

        if(ans) System.out.println("Yes");
        else System.out.println("No");
    }

    boolean checkArray(int x, int y){
        boolean check = false;
        if((x-1 >= 0 && array[x-1][y] == '#') ||
                (x+1<W && array[x+1][y] == '#') ||
                (y-1>=0 && array[x][y-1] == '#') ||
                (y+1<H && array[x][y+1] == '#')
                ){
            check = true;
        }
        return check;
    }
}
