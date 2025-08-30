
import java.util.*;
public class Main {
	boolean [] isprime;
	int MAX = 1000000;
	
	private void doit(){
		Scanner sc = new Scanner(System.in);
		eratos();
		while(true){
			int n = sc.nextInt();
			int x = sc.nextInt();
			if((n | x) == 0) break;
			int [] data = new int[n];
			for(int i = 0; i < n; i++){
				data[i] = sc.nextInt();
			}
			
			boolean [] dp = new boolean[x+1];
			dp[0] = true;
			int max = -1;
			for(int i = 0; i < n; i++){
				
				for(int j = 0; j <= x; j++){
					if(dp[j]){
						if(j + data[i] > x) continue;
						dp[j + data[i]] = true;
						if(isprime[j + data[i]]){
							max = Math.max(max, j + data[i]);
						}
					}
				}
			}
			System.out.println(max == -1 ? "NA" : max);
		}
	}
	
	private void eratos() {
		isprime = new boolean[MAX + 1];
		Arrays.fill(isprime, true);
		
		for(int i = 2; i * i <= MAX; i++ ){
			if(isprime[i]){
				for(int j = i + i; j <= MAX; j += i){
					isprime[j] = false;
				}
			}
		}
	}

	public static void main(String[] args) {
		Main obj = new Main();
		obj.doit();
	}
}