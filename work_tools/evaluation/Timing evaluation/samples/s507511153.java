import java.util.*;
public class Main{
	public static void main(String[] args){
      Scanner sc = new Scanner(System.in);
      int x = sc.nextInt();
      int a = sc.nextInt();
      int b = sc.nextInt();
      int l1 = Math.abs(x-a);
      int l2 = Math.abs(x-b);
      if(l1 < l2) System.out.println("A");
      else System.out.println("B");
    }
}
