import java.util.Arrays;
import java.util.Scanner;

class Main {
	public static int count_min=10000;
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int n = scan.nextInt();
		int m = scan.nextInt();
		int coin[] = new int [m];
		for(int i=0;i<m;++i){
			coin[i]=scan.nextInt();
		}
		Arrays.sort(coin);
		int flag=0;
		while(n>coin[m-1]*10){
			n-=coin[m-1];
			flag++;
		}
		countWaysDP(coin,n,m,flag);
		System.out.println(count_min);
	}
	
	public static void countWaysDP(int[] coin,int n, int m, int count){
		if(count_min>count){
			int flag=1;
			int[] map = new int[m];
			for(int i=0;i<m;++i){
				map[i]=n;
			}
			count++;
			for(int i=m-1;i>=0;--i){
				if(flag==0){
					break;
				}
				map[i]=map[i]-coin[i];
				if(map[i]<0){
					m--;
				}
				if(map[i]==0){
					flag=0;
					m=0;
					count_min=count;
				}
			}
			for(int i=m-1;i>=0;--i){
				if(m>0&&flag==1){
					countWaysDP(coin,map[i],i+1,count);
				}
			}
		}
	}
}