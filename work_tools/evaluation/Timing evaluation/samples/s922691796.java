import java.util.Scanner;

public class Main {
    public static void main(String args[]){
        Scanner stdIn =  new Scanner(System.in);
        
        while(true){
            double x = stdIn.nextInt();
            double h = stdIn.nextInt();
            
            if(x == 0 && h == 0){
                break;
            }
            
            System.out.println(x * x + x * Math.sqrt(h * h + x / 2 * x / 2) * 0.5 * 4);
        }
    }
}

