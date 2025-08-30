import java.util.*;
import java.io.*;

public class Main{
    public static void main(String[] args){
        Scanner ir=new Scanner(System.in);
        outer:
        for(;;){
            int m=ir.nextInt();
            int n=ir.nextInt();
            if(m==0&&n==0){
                System.exit(0);
            }
            int MAX=10000000;
            boolean[] f=new boolean[MAX];
            int ct=0;
            for(int i=m;;i++){
                if(!f[i]){
                    ct++;
                    if(ct>n){
                        System.out.println(i);
                        continue outer;
                    }
                    for(int j=i;j<MAX;j+=i){
                        f[j]=true;
                    }
                }
            }
        }
    }
}
