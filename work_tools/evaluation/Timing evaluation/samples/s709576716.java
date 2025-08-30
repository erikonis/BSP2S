import java.util.*;
public class Main{
public static void main(String[] args){
Scanner sc = new Scanner(System.in);
  int Q = sc.nextInt();
  for(int i=0;i<Q;i++){
    long f = sc.nextLong();
    long s = sc.nextLong();
    long a = Math.max(f, s);
    long b = Math.min(f, s);
    long K = a*b;
    long L = (long) Math.sqrt(K);
    long M = (K-1)/L;
    long N = Math.max(-1,M-b-1);
    System.out.println(L+N+b-1);
  }
}}
