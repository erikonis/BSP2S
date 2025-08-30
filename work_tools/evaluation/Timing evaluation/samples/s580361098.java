import java.util.*;

public class Main {
	void solve() {
		Scanner scan = new Scanner(System.in);
		int d = scan.nextInt();
		System.out.print("Christmas ");
		for(int i = 0; i < 25 - d; i++) {
			System.out.print("Eve ");
		}
		scan.close();
	}
	
	public static void main(String[] args) {
		new Main().solve();
	}
}
