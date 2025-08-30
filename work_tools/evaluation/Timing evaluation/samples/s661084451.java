import java.util.*;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for(int i = 0;i<n;i++)a[i]=sc.nextInt();
        int ans = 0;
        for(int i = 0;i<n;i++){
            if(a[a[i]-1]==i+1)ans++;
        }
        System.out.println(ans/2);
    }
}