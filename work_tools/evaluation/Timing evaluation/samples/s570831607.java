import java.awt.geom.Point2D;
import java.util.*;
public class Main {
	Scanner in = new Scanner(System.in);
	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		new AOJ1158().doit();
	}

	class AOJ1158{
		int w,h;
		int[][] controll;
		int[] cost;
		Point2D go(int x,int y,int muki){
			Point2D result = new Point2D.Double();
			if(muki==0)result = new Point2D.Double(x,y-1);
			else if(muki==1)result = new Point2D.Double(x+1, y);
			else if(muki==2)result = new Point2D.Double(x, y+1);
			else if(muki==3)result = new Point2D.Double(x-1, y);
			return result;
		}

		int solve(){
			int result = 0;
			int[][][] map = new int[31][31][4];
			for(int i=0;i<31;i++)for(int s=0;s<31;s++)Arrays.fill(map[i][s],Integer.MAX_VALUE/2);
			PriorityQueue<State> q = new PriorityQueue<State>();
			q.add(new State(0, 0, 1, 0));
			map[0][0][1] = 0;
			while(q.size()>0){
				State now = q.remove();
				int x = now.x;
				int y = now.y;
				int muki = now.muki;
//				System.out.println("*******************");
//				System.out.println(x+" "+y+" "+muki+" "+now.step);
				if(x==w-1&&y==h-1){
					result = now.step;
					break;
				}
				for(int i=0;i<4;i++){//0=直進,1=右折,2=反転,3=左折
					int nmuki = (muki+i)%4;
					Point2D nxy = go(x, y, nmuki);
					int nx = (int)nxy.getX();
					int ny = (int)nxy.getY();
					int nstep = now.step;
					if(nx<0||nx>=w||ny<0||ny>=h)continue;
					if(controll[y][x]!=i)nstep += cost[i];
//					if(nx==7&&ny==0){
//						System.out.println("------");
//						System.out.println(nx+" "+ny+" "+nmuki+" "+nstep+" "+i+" "+controll[ny][nx]);
//					}
					if(map[ny][nx][nmuki]<=nstep)continue;
					map[ny][nx][nmuki] = nstep;
					q.add(new State(nx, ny, nmuki, nstep));
				}
			}
//			System.out.println();
//			for(int i=0;i<h;i++){
//				for(int s=0;s<w;s++)System.out.print(map[i][s][0]+" ");
//				System.out.println();
//			}
//			System.out.println();
//			for(int i=0;i<h;i++){
//				for(int s=0;s<w;s++)System.out.print(map[i][s][1]+" ");
//				System.out.println();
//			}
//			System.out.println();
//			for(int i=0;i<h;i++){
//				for(int s=0;s<w;s++)System.out.print(map[i][s][2]+" ");
//				System.out.println();
//			}
//			System.out.println();
//			for(int i=0;i<h;i++){
//				for(int s=0;s<w;s++)System.out.print(map[i][s][3]+" ");
//				System.out.println();
//			}
			return result;
		}
		class State implements Comparable<State>{
			int x,y,muki,step;
			public State(int x,int y,int muki,int step) {
				this.x = x;
				this.y = y;
				this.muki = muki;
				this.step = step;
			}
			public int compareTo(State arg0) {
				return this.step - arg0.step;
			}
		}

		void doit(){
			while(true){
				w = in.nextInt();
				h = in.nextInt();
				if(w+h==0)break;
				controll = new int[h][w];
				cost = new int[4];//直進、右折、反転、左折
				for(int i=0;i<h;i++)for(int s=0;s<w;s++)controll[i][s] = in.nextInt();
				for(int i=0;i<4;i++)cost[i] = in.nextInt();
				int ans = solve();
//				System.out.print("Answer= " );
				System.out.println(ans);
				//break;
			}
		}
	}

}