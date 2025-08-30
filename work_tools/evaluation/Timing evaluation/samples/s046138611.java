import java.util.*;

class Main{
  public static void main(String args[]){
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    int t = sc.nextInt();
	int a = sc.nextInt();
    double h1 = 999;
    int answer = 0;
    for(int i = 0; i < n; i++){
      int h = sc.nextInt();
	  double b = t-h*0.006;
	  if(Math.abs(b-a) < h1){
	  	h1 = Math.abs(b-a);
		answer = i+1;
	  }
    }
    System.out.print(answer);
  }
}
