import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
public class Main {
	public static void main(String[] args){
      	
		Scanner sc = new Scanner(System.in);

    long N = sc.nextLong();
    long K = sc.nextLong();
    if (N/K>10000000) {
      N =N%K;
      if (Math.abs(N-K)<N) {
        N = Math.abs(N-K);
      }
    }else{
     while (Math.abs(N-K)<N) {
        N =Math.abs(N-K);
      }
    }  
      System.out.println(N);
      sc.close();
    }
		

}