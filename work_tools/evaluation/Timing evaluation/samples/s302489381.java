import java.util.*;

public class Main{
 public static void main(String[]a){
Scanner sc = new Scanner(System.in);
for(;;){
int n = sc.nextInt();
if(n ==0 ) {
break;
}
int[] p = new int[n];
int[] d = new int[n];
int max = 0;
int res = 0;
for(int i =0 ; i < n ; i++){
p[i] = sc.nextInt();
d[i] = sc.nextInt()+sc.nextInt();
if(max < d[i]){
max = d[i];
res = p[i];
}
}
System.out.println(res+" "+max);
}
}

} 