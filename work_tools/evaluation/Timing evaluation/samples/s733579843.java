import java.util.Scanner;
public class Main
{
	public static void main(String[] args) 
	{
		Scanner in=new Scanner(System.in);
		double x[]=new double[21];
		double y[]=new double[21];
		int i=0;
		double S=0;
		while(in.hasNext())
		{
			String s[]=in.next().split(",");
			x[i]=Double.valueOf(s[0]);
			y[i]=Double.valueOf(s[1]);
			i++;
		}
		x[i]=x[0];
		y[i]=y[0];
		for(int j=0;j<i;j++)
		{
			double seki=(x[j]-x[j+1])*(y[j]+y[j+1]);
			S+=seki;
		}
		System.out.printf("%.6f\n",Math.abs(S/2));
	}
}