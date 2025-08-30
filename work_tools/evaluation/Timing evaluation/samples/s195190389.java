import java.util.*;
 
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        String s1 = sc.next();
        String ans = s1.substring(0,1);
        String s2 = sc.next();
        ans+=s2.substring(0,1);
        String s3 = sc.next();
        ans+=s3.substring(0,1);
        
        System.out.print(ans.toUpperCase());
   }
}
