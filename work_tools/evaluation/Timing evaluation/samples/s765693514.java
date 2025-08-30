import java.util.*;
public class Main {
  public static void main(String[] args){
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    double keisan = (n/1.08);
    long x = Math.round(keisan);
    long sum = (long)(x*1.08);
    long sum2 = (long)((x+1)*1.08);
    long sum3 = (long)((x-1)*1.08);
    
    if(n == sum){
      System.out.println(x);
    }else if(n == sum2){
      System.out.println(x+1);
    }else if(n == sum3){
      System.out.println(x-1);
    }else{
      System.out.println(":(");
    }
  }
}