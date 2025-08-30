import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int a,b,c;
        int ck=0;
        
        a = sc.nextInt();
        b = sc.nextInt();
        c = sc.nextInt();
        
        for(;a<=b;a++){
            if(c%a == 0){
                ck++;
            }
        }
        System.out.println(ck);
        
        sc.close();
    }
}
