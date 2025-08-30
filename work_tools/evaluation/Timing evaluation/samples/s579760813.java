import java.util.*;

public class Main{
  public static void main(String[] args){
    Scanner sc = new Scanner(System.in);
    int x1 = sc.nextInt();
    int y1 = sc.nextInt();
    int x2 = sc.nextInt();
    int y2 = sc.nextInt();
    int x3 = x2+y1-y2;
    int y3 = y2+x2-x1;
    int x4 = x1+y1-y2;
    int y4 = y1+x2-x1;
    System.out.printf("%d %d %d %d\n", x3, y3, x4, y4);
  }
}