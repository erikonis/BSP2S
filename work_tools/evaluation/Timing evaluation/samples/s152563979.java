import java.util.Scanner;


public class Main {
	
	Scanner sc = new Scanner(System.in);
	public void run() {
		while(sc.hasNext()){
			int n = sc.nextInt();
			int m = sc.nextInt();
			int p = sc.nextInt();
			if(n == 0 && m == 0 && p == 0){
				break;
			}
			else calc(n,m,p);
		}
	}
	public void calc(int n, int m, int p){
		int sum = 0;
		int x = 0;
		for(int i = 1; i < n + 1; i++){
			int xi = sc.nextInt();
			sum = sum + xi;
			if(m == i) x = xi;
		}
		if(x == 0) System.out.println(0);
		else{
			int ans = (sum * 100 * (100 - p) / 100) / x;
			System.out.println(ans);
		}
	}
	public static void main(String[] args) {
		new Main().run();
	}
}