import java.util.Scanner;
public class Main
{
	public static void main(String[] args) 
	{
		Scanner in=new Scanner(System.in);
		for(;;)
		{
			int a1=in.nextInt(),m1=in.nextInt();
			int a2=in.nextInt(),m2=in.nextInt();
			int a3=in.nextInt(),m3=in.nextInt();
			int x=1,y=1,z=1;
			if((a1|a2|a3|m1|m2|m3)==0)
				return;
			long c1=modoru(x,a1,m1);
			long c2=modoru(y,a2,m2);
			long c3=modoru(z,a3,m3);
			c2=lcm(c1,c2);
			c3=lcm(c2,c3);
			System.out.println(c3);
		}
	}
	public static int modoru(int x,int a,int m)
	{
		int cnt=0;
		for(;;)
		{
			cnt++;
			x=(a*x)%m;
			if(x==1)
				break;
		}
		return cnt;
	}
	
	public static long gcd(long a,long b)
	{
		return b==0?a:gcd(b, a%b);
	}
	public static long lcm(long a,long b)
	{
		
		long gcd=gcd(a,b);
		long lcm=a/gcd*b;
		return lcm;
	}
}