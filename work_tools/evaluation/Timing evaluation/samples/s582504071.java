import java.util.Scanner;
import java.math.BigDecimal;
 
class Main{
public static void main(String[] args){
Scanner sc=new Scanner(System.in);
double x=0;
double y=0;
double rad=Math.PI/2;
while(sc.hasNext()){
String[] s=sc.next().split(",");
int dis=Integer.parseInt(s[0]);
x+=dis*Math.cos(rad);
y+=dis*Math.sin(rad);
int deg=Integer.parseInt(s[1]);
rad-=Math.toRadians(deg);
}
BigDecimal a=new BigDecimal(x);
BigDecimal b=new BigDecimal(y);
a=a.setScale(0,BigDecimal.ROUND_DOWN);
b=b.setScale(0,BigDecimal.ROUND_DOWN);
System.out.println(a);
System.out.println(b);
}
}