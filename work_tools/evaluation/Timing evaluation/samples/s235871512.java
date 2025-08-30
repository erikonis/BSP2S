import java.util.*;

class Main {

	static int ub = 1000010;
	static boolean isPrime[] = new boolean[ub];

	public static void main(String args[]){
		Scanner in = new Scanner(System.in);
		sieve();
		while(in.hasNext()){
			int a = in.nextInt(), d = in.nextInt(), n = in.nextInt();
			if(a==0 && d==0 && n==0) return ;
			int cnt = 0;
			for(int i = a;;i += d)if(isPrime[i] && ++cnt==n){
				System.out.println(i);
				break;
			}
		}
	}

	static void sieve(){
		for(int i=2; i<ub; i++) isPrime[i] = true;
		for(int i=2; i*i<ub; i++)if(isPrime[i]){
			for(int j=i*2; j<ub; j+=i){
				isPrime[j] = false;
			}
		}
	}
}