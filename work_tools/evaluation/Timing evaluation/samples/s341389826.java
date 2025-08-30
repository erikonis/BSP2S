import java.util.*;
public class Main{
	public static void main(String[] args){
    	Scanner sc = new Scanner(System.in);
       	int N = sc.nextInt();
        //String[] results = new String[N];
        int a=0,t=0,w=0,r=0;
      	for(int i=0;i<N;i++){
        	String temp=sc.next();
            if(temp.equals("AC")){
            	a++;
            }
            else if(temp.equals("WA")){
            	w++;
            }
          	else if(temp.equals("TLE")){
            	t++;
            }
          	else if(temp.equals("RE")){
            	r++;
            }
        }
      	
        System.out.println("AC"+ " x "+ a);
      	System.out.println("WA"+ " x "+ w);
      	System.out.println("TLE"+ " x "+ t);
      	System.out.println("RE"+ " x "+ r);
      
    }
}