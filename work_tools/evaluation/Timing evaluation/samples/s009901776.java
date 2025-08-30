import java.util.*;
 
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String S =sc.nextLine();
        
        String spt[]=S.split(",");
        
        for(int i=0;i<spt.length;i++){
            System.out.print(spt[i]+" ");
        }
        
   }
}
