import java.util.Scanner;
public class Main
{
	public static void main(String[] args) 
	{
		Scanner in=new Scanner(System.in);
		int N=in.nextInt();
		while(N-->0)
		{
			double xp1=in.nextDouble(),yp1=in.nextDouble();
			double xp2=in.nextDouble(),yp2=in.nextDouble();
			double xp3=in.nextDouble(),yp3=in.nextDouble();
			double xk=in.nextDouble(),yk=in.nextDouble();//enemy
			double xs=in.nextDouble(),ys=in.nextDouble();//me
			int kabe=0;
			
			if(judge(xp1,yp1,xp2,yp2,xk,yk,xs,ys)>0||judge(xk,yk,xs,ys,xp1,yp1,xp2,yp2)>0)
				;
			else
				kabe++;
			if(judge(xp2,yp2,xp3,yp3,xk,yk,xs,ys)>0||judge(xk,yk,xs,ys,xp2,yp2,xp3,yp3)>0)
				;
			else
				kabe++;
			if(judge(xp3,yp3,xp1,yp1,xk,yk,xs,ys)>0||judge(xk,yk,xs,ys,xp3,yp3,xp1,yp1)>0)
				;
			else
				kabe++;
			System.out.println(kabe==1 ? "OK":"NG");
		}
	}
	
	static double judge(double x1,double y1,double x2,double y2,double x3,double y3,double x4,double y4)
	{
		double tc=(x1-x2)*(y3-y1)+(y1-y2)*(x1-x3);
		double td=(x1-x2)*(y4-y1)+(y1-y2)*(x1-x4);
		return tc*td;
	}
}