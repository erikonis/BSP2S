import java.util.Scanner;
public class Main
{
	public static void main(String arg[])
	{
		Scanner in = new Scanner(System.in);
		int a[][]=new int[5][5];
		int n=in.nextInt();
		while(n-->0)
		{
			for(int i=0; i<5; i++)
				for(int j=0; j<5; j++)
					a[i][j]=in.nextInt();
			int max=0;
			for(int i=0; i<5; i++)
			{
				for(int j=0; j<5; j++) //ここまで始点決定の記述
				{
					for(int h=i;h<5;h++)
					{
						for(int w=j;w<5;w++)
						{
							boolean flag =true;
							int co=0;
							for(int x=i;x<=h;x++)
								for(int y=j;y<=w;y++)
								{
									if(a[x][y]==0)
										flag = false;
									else
										co++;
								}
							if(flag)
								max=Math.max(max, co);
						}
					}
				}
			}
			System.out.println(max);
		}
	}
}