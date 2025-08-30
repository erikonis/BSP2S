import java.util.*;
import java.lang.Math;

public class Main {
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
      	int n = sc.nextInt();
      	String s = sc.next();
      	long count = 0;
      	long rc = 0;
      	long gc = 0;
      	long bc = 0;
      	int istack;
      
      	Map cMap = new HashMap<Character,Integer>() {
          {
        	put('R',1);
        	put('G',10);
        	put('B',100);
          }
        };    	
      
      	for(int i = 0;i < n;i++){
        	if(s.charAt(i) == 'R') rc++;
          	else if(s.charAt(i) == 'G') gc++;
          	else if(s.charAt(i) == 'B') bc++;
        }
 
      	for(int i = 0;i < n;i++){
          for(int j = i+1;j < n;j++){
            if(j+(j-i) < n){
              istack = (int)cMap.get(s.charAt(i))
                      +(int)cMap.get(s.charAt(j))
                      +(int)cMap.get(s.charAt(j+(j-i)));
              if(istack == 111) count++;
            }
          }
        }
      
      	System.out.println(rc*gc*bc-count);
	}
}
