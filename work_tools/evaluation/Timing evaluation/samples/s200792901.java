import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		doIt();
	}
	public static void doIt(){
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		for(int k = 0; k < n; k++){
			int year = sc.nextInt();
			int month = sc.nextInt();
			int day = sc.nextInt();
			System.out.println(days(1000,1,1) - days(year,month,day));	
		}
	}
	
	public static int days(int year, int month, int day){
		int ret = 0;
		int y = 1;
		int m = 1;
		while(y < year){
			if(y % 3 != 0) ret += 19*5 + 20*5;
			else ret += 20*10;
			y++;
		}
		boolean bBig = false;
		if(year % 3 == 0) bBig = true;
		while(m < month){
			if(bBig) ret += 20;
			else
				if(m % 2 == 0) ret += 19;
				else ret += 20;
			m++;
		}	
		return ret + day;
	}
}