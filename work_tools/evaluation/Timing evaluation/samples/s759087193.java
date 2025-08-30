import java.util.*;
public class Main {
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int a = sc.nextInt();
		if(a < 600){
			System.out.println("8");
        } else if(a < 800){
          	System.out.println("7");
        } else if(a < 1000){
          	System.out.println("6");
        } else if(a < 1200){
         	System.out.println("5");
        } else if(a < 1400){
          	System.out.println("4");
        } else if(a < 1600){
          	System.out.println("3");
        } else if(a < 1800){
          	System.out.println("2");
        } else {
        	System.out.println("1");
        }
	}
}