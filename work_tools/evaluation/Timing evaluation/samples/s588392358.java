import java.util.*;
import java.io.*;
public class Main {

	public static void main(String[] args) throws Exception{
		long num = 1000000007;
		long[] fact = new long[2001];
 		fact[0] = 1;
 		long ans1 = 1;
 		for(int i = 1;i<=2000;i++){
 			ans1= (ans1*i) % num;
 			fact[i] = ans1;
 		}
 		long[] inv = new long[2001];
 		inv[0] = 1;
 		for(int i = 1;i<=2000;i++){
 			inv[i] = power(fact[i], num-2, num)%num;
 		}
		// TODO Auto-generated method stub
 		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
 		PrintWriter out = new PrintWriter(System.out);
 		int s = Integer.parseInt(bf.readLine());
 		long sum = 0;
 		if (s >= 3)
 			sum = 1;
 		int count = 1;
 		for(int j = 5;j<=s;j+=2){
 			if (s-j >= count){
 				long prod = 1;
 				prod*=fact[s-j];
 				prod%=num;
 				prod*=inv[count];
 				prod%=num;
 				prod*=inv[s-j-count];
 				prod%=num;
 				sum+=prod;
 				sum%=num;
 			}
 			count++;
 		}
 		
 		
 		out.println(sum);
	 		
 		out.close();
 		
 		
 		
 	}
	public static int power(long x, long y, long mod){
		long ans = 1;
		while(y>0){
			if (y%2==1)
				ans = (ans*x)%mod;
			x = (x*x)%mod;
			y/=2;
		}
		return (int)(ans);
	}
}
 	
 
//StringJoiner sj = new StringJoiner(" "); 
//sj.add(strings)
//sj.toString() gives string of those stuff w spaces or whatever that sequence is

 		
 		
 		
 		
	


