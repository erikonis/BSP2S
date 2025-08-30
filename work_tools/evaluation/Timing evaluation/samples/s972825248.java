import java.util.Scanner;
public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
      int x1 = scan.nextInt();
      int x2 = scan.nextInt();
      int x3 = scan.nextInt();
      int x4 = scan.nextInt();
      int x5 = scan.nextInt();
      int ans = 0;
      
      if(x1 == 0)
        ans = 1;
      else if(x2 == 0)
        ans = 2;
      else if(x3 == 0)
        ans = 3;
      else if(x4 == 0)
        ans = 4;
      else
        ans = 5;
      
      System.out.println(ans);
   }
}