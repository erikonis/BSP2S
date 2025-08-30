import java.util.Scanner;

class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int x = sc.nextInt();
    int y = sc.nextInt();
    
    if(x < y){
    	int tmp = x;
    	x = y;
    	y = tmp;
    }

    while(x % y != 0){
    	int tmp = x % y;
    	x = y;
    	y = tmp;
    }
	System.out.println(y);
    
  }
}