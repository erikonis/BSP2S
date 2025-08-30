import java.util.*;

public class Main {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		while(true){
			int x = sc.nextInt();
			int h = sc.nextInt();
			if(x==0 && h==0)break;
			
			double a = Math.sqrt(((double)x/2)*((double)x/2) + h*h);
			double sum = x*x + x*a*2;
			System.out.println(sum);
		}
	}	
}