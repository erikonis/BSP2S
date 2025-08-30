import java.util.Scanner;
public class Main{
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int t = 0;
        int h = 0;
        for(int x = 0; x < n; x++){
            String tc = scan.next();
            String hc  = scan.next();
            if(tc.compareToIgnoreCase(hc) == 0){
                t++;
                h++;
            }
            else if(tc.compareToIgnoreCase(hc) > 0){
                t = t + 3;
            }
            else{
                h = h + 3;
            }
        }
        System.out.println(t + " " + h);
    }
}
