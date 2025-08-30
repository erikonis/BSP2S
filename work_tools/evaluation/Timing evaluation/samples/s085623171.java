import static java.lang.System.*;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

class Main {
	public static Random rand=new Random();

	class Pos{
		int lx,ly,rx,ry;
		public Pos(int _lx,int _ly,int _rx,int _ry) {
			lx=_lx;ly=_ly;rx=_rx;ry=_ry;
		}
	}

	int[] dx1=new int[]{1,0,-1,0};
	int[] dy1=new int[]{0,1,0,-1};
	int[] dx2=new int[]{-1,0,1,0};
	int[] dy2=new int[]{0,1,0,-1};


	public void run() {
		Case:while(true){
			int W=sc.nextInt(),H=sc.nextInt();
			if(W==0 && H==0)return;
			char[][] map1=new char[H][W],map2=new char[H][W];
			for(int i=0;i<H;i++){
				map1[i]=sc.next().toCharArray();
				map2[i]=sc.next().toCharArray();
			}

			boolean[][][][] passed=new boolean[H][W][H][W];

			int lx=-1,ly=-1,rx=-1,ry=-1;
			for(int h=0;h<H;h++)for(int w=0;w<W;w++){
				if(map1[h][w]=='L'){
					lx=w;ly=h;
				}
				if(map2[h][w]=='R'){
					rx=w;ry=h;
				}
			}

			Queue<Pos> que=new LinkedList<Pos>();
			que.add(new Pos(lx, ly, rx, ry));
			passed[ly][lx][ry][rx]=true;

			while(!que.isEmpty()){
				Pos p=que.poll();
				//4方向
				for(int i=0;i<4;i++){
					int nx1=p.lx+dx1[i],ny1=p.ly+dy1[i];
					int nx2=p.rx+dx2[i],ny2=p.ry+dy2[i];
					if(!(0<=nx1 && nx1 <W &&0<=ny1 && ny1<H)){
						nx1=p.lx;ny1=p.ly;
					}
					if(!(0<=nx2 && nx2<W &&0<=ny2 && ny2<H)){
						nx2=p.rx;ny2=p.ry;
					}
					if(map1[ny1][nx1]=='#'){
						nx1=p.lx;ny1=p.ly;
					}
					if(map2[ny2][nx2]=='#'){
						nx2=p.rx;ny2=p.ry;
					}
					if(map1[ny1][nx1]=='%' && map2[ny2][nx2]=='%'){
						ln(str(true));continue Case;
					}else if(map1[ny1][nx1]=='%' || map2[ny2][nx2]=='%'){
						continue;
					}
					if(!passed[ny1][nx1][ny2][nx2]){
						que.add(new Pos(nx1,ny1,nx2,ny2));
						passed[ny1][nx1][ny2][nx2]=true;
					}
				}
			}
			ln(str(false));
		}
	}
	public static void main(String[] args) {
		new Main().run();
	}

	static Scanner sc=new Scanner(in);
	//output lib
	static final String br=System.getProperty("line.separator");
	static final String[] asep=new String[]{""," ",br,br+br};
	static String str(boolean o){
		return o?"Yes":"No";
	}
	static <K,V> String str(Map<K, V> map){
		StringBuilder sb=new StringBuilder();
		boolean isFirst=true;
		for(Entry<K,V> set:map.entrySet()){
			if(!isFirst)sb.append(br);
			sb.append(str(set.getKey())).append(":").append(str(set.getValue()));
			isFirst=false;
		}
		return sb.toString();
	}
	static <E> String str(Collection<E> list){
		StringBuilder sb=new StringBuilder();
		boolean isFirst=true;
		for(E e:list){
			if(!isFirst)sb.append(" ");
			sb.append(str(e));
			isFirst=false;
		}
		return sb.toString();
	}
	static String str(Object o){
		int depth=_getArrayDepth(o);
		if(depth>0)return _strArray(o,depth);
		return o.toString();
	}
	static int _getArrayDepth(Object o){
		if(!o.getClass().isArray() || Array.getLength(o)==0) return 0;
		return 	1+_getArrayDepth(Array.get(o,0));
	}
	//depth ex A[10]…1 A[10][10]…2 exception A[0]…0 A[10][0]…1 A[0][0]…0
	static String _strArray(Object o,int depth){
		if(depth==0) return str(o);
		StringBuilder sb=new StringBuilder();
		for(int i=0,len=Array.getLength(o);i<len;i++){
			if(i!=0)sb.append(asep[depth]);
			sb.append(_strArray(Array.get(o,i),depth-1));
		}
		return sb.toString();
	}
	static void pr(Object... os){
		boolean isFirst=true;
		for(Object o:os){
			if(!isFirst)out.print(" ");
			out.print(o);
			isFirst=false;
		}
	}
	static void ln(){
		out.println();
	}
	static void ln(Object... os){
		for(Object o:os){
			pr(o);ln();
		}
	}
}