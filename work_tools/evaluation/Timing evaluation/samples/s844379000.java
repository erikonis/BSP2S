import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int m = sc.nextInt();
            int f = sc.nextInt();
            int b = sc.nextInt();

            b -= m;

            if ( b > 0 ) {
                if ( b <= f ) {
                    System.out.println(b);
                } else {
                    System.out.println("NA");
                }
            } else {
                System.out.println(0);
            }
        }
    }
}

