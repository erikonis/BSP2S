import java.util.Arrays;
import java.util.Scanner;
public class Main
{
	public static void main(String[] args) 
	{
		Scanner in=new Scanner(System.in);
		int M=100000;
		boolean a[]=new boolean[M+1];
		Arrays.fill(a, true);
		a[0]=a[1]=false;
		for(int i=2;i*i<=M;i++)
		{
			if(!a[i])
				continue;
			for(int j=i*2;j<=M;j+=i)
				a[j]=false;
		}
		for(;;)
		{
			int n=in.nextInt();
			if(n==0)
				return;
			int ans=0;
			for(int i=0;i<=n;i++)
			{
				if(!a[i])
					continue;
				for(int j=i;j<=n;j++)
				{
					if(!a[j])
						continue;
					if(i+j==n)
						ans++;
				}
			}
			System.out.println(ans);
		}
	}
}