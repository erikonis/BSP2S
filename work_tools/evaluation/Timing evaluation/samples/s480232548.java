import java.util.*;
public class Main {
    public static void main(String[] args){
    
    Scanner sc = new Scanner(System.in);
    int N = sc.nextInt();
    int M = sc.nextInt();
    int X = sc.nextInt();
    int[]A = new int[M];
 
    int dai = 0;
    int syou = 0;
    for(int i=0; i<M; i++){
        A[i] = sc.nextInt();
        if(A[i] > X) {
        	dai++;
        }else if(A[i] < X){
        	syou++;
    }
}
       if(dai>syou) {
        	System.out.println(syou);
        }else {
        	System.out.println(dai);
    }
}
}