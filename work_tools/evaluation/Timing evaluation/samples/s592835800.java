import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        int K = scan.nextInt();
        int seven = 0;
        long count = 1;
        if(K%2 != 0 && K%5 != 0){
            while(true){
                seven = (10*seven + 7) % K;
                if(seven == 0){
                  System.out.println(count);
                  return;
                } 
                count++;
            }
        }
        System.out.println("-1");
    }
}