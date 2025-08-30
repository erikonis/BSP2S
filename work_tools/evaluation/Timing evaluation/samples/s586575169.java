import java.util.Arrays;
import java.util.Scanner;

public class Main
{
	public static void main(String arg[])
	{
		Scanner sc=new Scanner(System.in);
		boolean a[] =new boolean[10001];
		Arrays.fill(a, true);
		a[0]=a[1]=false;
		for(int i=2; i*i<=10000; i++)
		{
			if(!a[i])
				continue;
			for(int j=i*2; j<=10000; j+=i)
				a[j]=false;
		}
		while(sc.hasNext())
		{
			int n=sc.nextInt();
			if(n==0)
				return;
			int sum=0;
			int ans=0;
			for(int i=2; i<=n; i++)
			{
				sum =0;
				if(a[i])
					for(int j=i; j<=n; j++)
					{
						if(a[j])
						{
							sum+=j;
							if(n==sum)
								ans++;
						}
						if(sum>=n)
							break;
					}
			}
			System.out.println(ans);
		}
	}
}