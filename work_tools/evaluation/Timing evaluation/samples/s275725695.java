import java.util.*;
import java.text.*;

class Main {
    public static void main(String[]args)throws Exception {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for(int i = 1; i <= n; i++) {
            int x = i;
            if((x % 3) == 0) {
                System.out.print(" " + i);
            } else {
                while(x != 0) {
                    if((x % 10) == 3) {
                        System.out.print(" " + i);
                        x = 0;
                    }
                    x = x / 10;
                }
            }
        }
        System.out.println();
    }
}