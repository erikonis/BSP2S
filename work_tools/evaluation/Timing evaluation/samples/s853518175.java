import java.util.*;

class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        
        int N = Integer.parseInt(sc.next());
        int K = Integer.parseInt(sc.next());
        
        
        int ans =  K * (int)Math.pow(K-1, N-1);
        
        System.out.println(ans);
    }
}