
import java.util.Scanner;

class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
          int H = scan.nextInt();
          int R = scan.nextInt();
          int t;
          
          t = H+R;
          
          if(t==0){
              System.out.println(0);
          }else if (t>0){
              System.out.println(1);
          }else if(t<0){
              System.out.println(-1);
          }
          

    }
}
          
        
      

    





