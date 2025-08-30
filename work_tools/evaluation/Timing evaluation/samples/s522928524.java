import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

		Scanner sc = new Scanner(System.in);
		int[]prices = new int[3];
		prices[0] = sc.nextInt();
		prices[1] = sc.nextInt();
		prices[2] = sc.nextInt();
		Arrays.sort(prices);
		System.out.println(prices[0] + prices[1]);
		
		
		sc.close();
	}

}