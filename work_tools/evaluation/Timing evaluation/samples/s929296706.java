import java.util.*;

public class Main {
	public Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		new Main();
	}
	public Main() {
		new aoj1141().doIt();
	}
	class aoj1141{
		int prime[] = new int [1000001];
		boolean is_prime[] = new boolean [1000002];
		void sieve(int n){
			int p = 0;
			for(int i  =0;i <= n;i++) is_prime[i] = true;
			is_prime[0] = is_prime[1] = false;
			for(int i = 2;i < n;i++){
				if(is_prime[i]){
					prime[p++] = i;
					for(int j = 2 * i;j <= n;j += i)is_prime[j] = false;
				}
			}
 		}
		void doIt() {
			sieve(1000000);
			while (true) {
				int a = sc.nextInt();
				int d = sc.nextInt();
				int n = sc.nextInt();
				if(a+d+n == 0)break;
				int ans = 0;
				while(true){
					if(is_prime[a])ans++;
					if(ans == n)break;
					a = a + d;
				}
				System.out.println(a);
			}
		}
	}
}