import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Pairs{
	private int x;
	private int y;

	//コンストラクタ
	public Pairs(int xx, int yy) {
		x = xx;
		y = yy;
	}

	//ゲットとセット--------------------------
	public int getX() { return x;}
	public int getY() { return y;}
}

class Main {

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)){
			int n = sc.nextInt();
			Pairs[] array = new Pairs[n];  //二次元配列にする：n個{x,y,小数}
			for (int i=0; i<n; i++) {
				int xx = sc.nextInt();
				int yy = sc.nextInt();
				array[i] = new Pairs(xx,yy);
			}
			Arrays.sort(array,new Comparator<Pairs>() {
				@Override
				public int compare(Pairs p0, Pairs p1) {
					if (p0.getX() > p1.getX()) return 1;
					else if (p0.getX() < p1.getX()) return -1;
					else {  //xがおなじ
						if (p0.getY() > p1.getY()) return 1;
						else return -1;
					}

				}
			});
			for (int i=0; i<n; i++) {
				System.out.println(array[i].getX() + " " + array[i].getY());
			}
		}
	}
}

