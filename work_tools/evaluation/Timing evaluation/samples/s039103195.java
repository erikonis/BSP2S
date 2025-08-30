import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO ツ篠ゥツ督ョツ青カツ青ャツつウツづェツつスツδソツッツドツ・ツスツタツブ
		Scanner sc=new Scanner(System.in);
		long a,b,g,l;
		while(sc.hasNext()){
			a=sc.nextLong();
			b=sc.nextLong();
			if(a<b){
				g=gcd(a,b);
			}
			else{
				g=gcd(b,a);
			}
			l=a*b/g;
			System.out.printf("%d %d\n",g,l);
		}
	}
	static long gcd(long a,long b){
		if(b==0){
			return a;
		}
		else{
			return gcd(b,a%b);
		}
	}
	

}