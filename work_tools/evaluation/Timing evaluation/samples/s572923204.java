import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int N = sc.nextInt();
        int W = sc.nextInt();
        int H = sc.nextInt();
        int numways = ((N-W)+1) * ((N-H)+1);
      if(numways<0){
        numways = 0;
      }
        System.out.println(numways);
        
    }

}
