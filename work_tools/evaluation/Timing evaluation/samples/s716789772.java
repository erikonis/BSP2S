import java.util.*;
 
public class Main {
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		int M1 = sc.nextInt();
		int D1 = sc.nextInt();
		int M2 = sc.nextInt();
		int D2 = sc.nextInt();
		int ans;
    if (M1 == M2) {
      ans = 0;
		}else {
			ans = 1;
		}
		System.out.println(ans);
  }
}