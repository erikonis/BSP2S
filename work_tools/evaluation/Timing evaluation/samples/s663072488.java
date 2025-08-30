
import java.util.*;
import static java.lang.System.*;

class Main {
	public static Scanner sc = new Scanner(in);
	public static Random rand=new Random();

	public void run() {
		TCase:while(true){
			int n=sc.nextInt();
			if(n==0)return;
			int W=sc.nextInt(),H=sc.nextInt();
			boolean[][] kaki=new boolean[W][H];
			for(int i=0;i<n;i++){
				kaki[sc.nextInt()-1][sc.nextInt()-1]=true;
			}
			int S=sc.nextInt(),T=sc.nextInt();

			int max=0;
			for(int xs=0;xs<W;xs++)for(int ys=0;ys<H;ys++){
				if(xs+S<=W && ys+T<=H){
					int sum=0;
					for(int x=xs;x<xs+S;x++)for(int y=ys;y<ys+T;y++){
						if(kaki[x][y])sum++;
					}
					max=Math.max(sum,max);
				}
			}

			ln(max);
		}
	}
	public static void main(String[] args) {
		new Main().run();
	}

	public int[] nextIntArray(int n){
		int[] res=new int[n];
		for(int i=0;i<n;i++){
			res[i]=sc.nextInt();
		}
		return res;
	}
	public static void pr(Object o) {
		out.print(o);
	}
	public static void ln(Object o) {
		out.println(o);
	}
	public static void ln() {
		out.println();
	}
}