import java.util.*;

public class Main {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		int n=sc.nextInt();
		int q=sc.nextInt();
		int h=n,w=n;
		int row[]=new int[h+1];
		int col[]=new int[w+1];
		Arrays.fill(row,w);
		Arrays.fill(col,h);
		long ans=(long)(n-2)*(n-2);
		while(q-->0) {
			int x=sc.nextInt();
			int y=sc.nextInt();
			if(x==1) {
				if(y<w) {
					for(int i=y;i<w;i++)
						col[i]=h;
					w=y;
				}
				ans-=(col[y]-2);
			}
			else {
				if(y<h) {
					for(int i=y;i<h;i++)
						row[i]=w;
					h=y;
				}
				ans-=(row[y]-2);
			}
		}
		System.out.println(ans);
	}
}