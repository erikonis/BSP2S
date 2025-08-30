import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n, m;
		n = sc.nextInt();
		m = sc.nextInt();
		sc.close();
		int ans;
		int tmp = m * 1900 + (n - m) * 100;
		int r = (int)Math.pow(2,  m);
	//	System.out.println(tmp);
		ans = (r * tmp);
		System.out.println(ans);
	}

}
