import java.util.*;
public class Main {
      public static void main(String[] args){
    		Scanner sc = new Scanner(System.in);
    		
    		int x = sc.nextInt();
        
    		int a = sc.nextInt(); 
        
    		int b = sc.nextInt();

       		int c = (x-a)/b;
       		
        	int ans = (x-a)-(c*b); 
    		
    
    		System.out.println(ans);
    	}

}