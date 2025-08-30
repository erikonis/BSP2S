import java.io.*;

class Main{
public static void main(String[] args) throws IOException{
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
String line="";
int[][] num;
while((line=br.readLine())!=null){
num=new int[10][10];
for(int i=0;i<10;i++){
num[9][i]=(int)(line.charAt(i)-'0');
}
for(int i=8;i>=0;i--){
for(int j=0;j<=i;j++){
num[i][j]=(num[i+1][j]+num[i+1][j+1])%10;
}
}
System.out.println(num[0][0]);
}
}
}