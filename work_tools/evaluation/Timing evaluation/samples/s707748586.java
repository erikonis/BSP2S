import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static final int BIG_NUM = 2000000000;
	public static final int MOD = 1000000007;
	public static final long HUGE_NUM = 99999999999999999L;
	public static final double EPS = 0.000000001;

	public static int H,W;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Scanner scanner = new Scanner(System.in);

		int diff_row[] = {-1,0,0,1},diff_col[] = {0,-1,1,0};
		StringBuffer ANS = new StringBuffer();

		while(true){

			H = scanner.nextInt();
			W = scanner.nextInt();

			if(H == 0 && W == 0)break;

			int table[][] = new int[H][W];

			for(int row = 0; row < H; row++){
				for(int col = 0; col < W; col++){

					table[row][col] = -1;
				}
			}

			char base_map[][] = new char[H][W];

			for(int row = 0; row < H; row++){

				String tmp_str = scanner.next();
				for(int col = 0; col < W; col++){

					base_map[row][col] = tmp_str.charAt(col);
				}
			}

			int ans = 0;
			Queue<Info> Q = new ArrayDeque<Info>();

			for(int row = 0; row < H; row++){
				for(int col = 0; col < W; col++){
					if(table[row][col] != -1)continue;

					table[row][col] = ++ans;

					Q.add(new Info(row,col));

					while(!Q.isEmpty()){

						for(int i = 0; i < 4; i++){

							int adj_row = Q.peek().row+diff_row[i];
							int adj_col = Q.peek().col+diff_col[i];

							if(rangeCheck(adj_row,adj_col) == false || base_map[adj_row][adj_col] != base_map[row][col] ||
									table[adj_row][adj_col] != -1)continue;

							table[adj_row][adj_col] = ans;
							Q.add(new Info(adj_row,adj_col));
						}

						Q.poll();
					}
				}
			}
			ANS.append(ans).append("\n");
		}

		System.out.print(ANS.toString());
    }

	public static boolean rangeCheck(int row,int col){

		return row >= 0 && row <= H-1 && col >= 0 && col <= W-1;
	}
}

class Info{

	public int row;
	public int col;

	Info(int arg_row,int arg_col){
		this.row = arg_row;
		this.col = arg_col;
	}
}

class UTIL{

	//最大公約数
	public static long gcd(long x,long y){

		x = Math.abs(x);
		y = Math.abs(y);

		if(x < y){

			long tmp = y;
			y = x;
			x = tmp;
		}

		if(y == 0){

			return x;
		}else{

			return gcd(y,x%y);
		}
	}

	//最小公倍数
	public static long lcm(long x,long y){

		return (x*y)/gcd(x,y);
	}

	//String→intへ変換
    public static int getNUM(String tmp_str){

        return Integer.parseInt(tmp_str);
    }

    //文字が半角数字か判定する関数
    public static boolean isNumber(String tmp_str){

        if(tmp_str == null || tmp_str.length() == 0){

            return false;
        }

        Pattern pattern = Pattern.compile("\\A[0-9]+\\z");
        Matcher matcher = pattern.matcher(tmp_str);
        return matcher.matches();
    }
}
