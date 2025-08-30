
import java.util.Scanner;

class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int D = scan.nextInt();
        int L= scan.nextInt();
        int h;
        int y;
        h = D/L;
        y=D-(h*L);
        System.out.println(h+y);
        
        }

    }



