import java.util.*;
public class Main{
static int[]check;//1=checked,0=yet,2=checking
static int[][]graph;
    static int[]dis;//-1=cannot arrive
static int n;
final static int MAX=100;
	public static void main(String[] args) {
		int i,j,s,pn,num;
		// TODO Auto-generated method stub
		Scanner sca = new Scanner(System.in);
		n=sca.nextInt();
		check=new int[MAX+1];
		dis=new int[MAX+1];
		graph=new int[MAX+1][MAX+1];
		for(i=1;i<=n;i++){
			num=sca.nextInt();
			pn=sca.nextInt();
			for(j=0;j<pn;j++){
				s=sca.nextInt();
				graph[num][s]=1;
			}
		}
		breadthexpl(1);
	}
	public static void breadthexpl(int now){
	    int i,j,hz;
	    Queue<Integer> que= new ArrayDeque<>();
	    que.offer(now);
	    while(que.peek()!=null){
		hz=que.poll();
		for(j=1;j<=n;j++){
		    if(graph[hz][j]!=0&&check[j]==0){
			check[j]=2;
			dis[j]=dis[hz]+1;
     		      if(dis[hz]==-1&&dis[j]!=-1)dis[hz]++;
			que.offer(j);
		    }
		    check[hz]=1;
		}
	    }
	    for(i=1;i<=n;i++){
		if(check[i]==0)dis[i]=-1;
		System.out.println(i+" "+dis[i]);
	    }
	    
	}

}
