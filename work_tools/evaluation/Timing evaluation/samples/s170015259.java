import java.util.*;
//reference http://www5d.biglobe.ne.jp/~tomoya03/shtml/algorithm/Intersection.htm
public class Main
{
	public static void main(String[] args) 
	{
		Scanner in=new Scanner(System.in);
		while(in.hasNext())
		{
			String s[]=in.next().split(",");
			double x[]=new double[4];
			double y[]=new double[4];
			for(int i=0;i<4;i++)
			{
				x[i]=Double.valueOf(s[i*2]);
				y[i]=Double.valueOf(s[i*2+1]);
			}
			if(judge(x[0],y[0],x[2],y[2],x[1],y[1],x[3],y[3])>0||judge(x[1],y[1],x[3],y[3],x[0],y[0],x[2],y[2])>0)
				System.out.println("NO");
			else
				System.out.println("YES");
		}
	}
	
	static double judge(double x1,double y1,double x2,double y2,double x3,double y3,double x4,double y4)
	{
		double tc=(x1-x2)*(y3-y1)+(y1-y2)*(x1-x3);
		double td=(x1-x2)*(y4-y1)+(y1-y2)*(x1-x4);
		return tc*td;
	}
	
	static public void debug(Object... o)
	{
		System.err.println(Arrays.deepToString(o));
	}
}