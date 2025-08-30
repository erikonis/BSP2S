import java.util.Scanner;

class Main{
	static long f(long n,long k){
		if(gcd(n,k)!=1){
			long g=gcd(n,k);
			return g*f(n/g,k/g);
		}
		else return 3*n-3;
	}
	static long gcd(long x,long y){
		if(Math.max(x,y)%Math.min(x,y)==0) return Math.min(x,y);
		else return gcd(Math.min(x,y),Math.max(x,y)%Math.min(x,y));
	}
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		while(sc.hasNext()){
			long n=sc.nextLong();
			long x=sc.nextLong();
			System.out.println(f(n,x));
		}
	}
}