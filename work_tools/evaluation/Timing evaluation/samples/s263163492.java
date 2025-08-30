import java.util.*;
public class Main{
		private static Scanner sc;
	    public static void main(String[] args){
	    	int A[] = new int[33];
	        A[0]=1;
	        for (int i=1; i<=32; i++){
	        	A[i]=A[i-1];
	        	if (i>1) A[i]+=A[i-2];
	        	if (i>2) A[i]+=A[i-3];
	        }
	        sc = new Scanner(System.in);
			int a;
			while ((a=sc.nextInt())>0){
				System.out.println((int)(A[a]/3650)+1);
	        }
	}
}