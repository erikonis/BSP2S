import java.util.Scanner;

public class Main{
  public static void main(String[] args){

Scanner sc = new Scanner(System.in);

while(true){

  int h = sc.nextInt();
  int w = sc.nextInt();

  if( h ==0 && w ==0 )break;


for(int i = 0;i < h; i++ ){
  for(int p = 0; p<w; p++){

if(i%2 ==0){
    if( p%2 == 0){
       System.out.print("#");
     }
    else{
     System.out.print(".");
   }
} else{
  if(p%2 ==1){
    System.out.print("#");
  }else {
    System.out.print(".");
  }

}


  }
System.out.println("");

}
System.out.println("");


}//while
  }//main


}//chess

