import java.util.*;
public class Main{
  public static void main(String[] args){
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    int k = sc.nextInt();
    int num = 1;
    for(int i = 0;i<n;i++){
      int n1 = num * 2;
      int n2 = num + k;
      if(n1 < n2){
        num = n1;
      }else{
        num = n2;
      }
    }
    System.out.println(num);
  }
}
