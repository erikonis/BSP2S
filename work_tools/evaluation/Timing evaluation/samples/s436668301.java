import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

		Scanner sc = new Scanner(System.in);

		//入力表示
		int A = sc.nextInt();
		int B = sc.nextInt();
		int ans = 0;
		int puls = 1;
		
		while (puls<B) {
			puls--;
			 puls +=A;
			 ans++;
			
		}
		System.out.println(ans);
	}
}