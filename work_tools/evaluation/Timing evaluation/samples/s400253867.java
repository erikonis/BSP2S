import java.util.Scanner;
public class Main {
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		double x1,y1,r1,x2,y2,r2,y,r,rs,a,b;
		int n;
		n=sc.nextInt();
		while(n--!=0){
			x1=sc.nextDouble();
			y1=sc.nextDouble();
			r1=sc.nextDouble();
			x2=sc.nextDouble();
			y2=sc.nextDouble();
			r2=sc.nextDouble();
			a=x2-x1;b=y2-y1;
			y=Math.sqrt(a*a+b*b);
			if(r2>r1)
				r=r2-r1;
			else
				r=r1-r2;

			rs=r1+r2;
			if(y<r){
				if(r2>r1){
					System.out.println("-2");
				}
				else{
					System.out.println("2");
				}
			}
			else if(y<=rs){
				System.out.println("1");
			}
			else{
				System.out.println("0");
			}
		}
	}
}