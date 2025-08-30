import java.util.Scanner;
public class Main {
	public static void main(String[] args){
		Main main = new Main();
		main.solveA();
	}

	private void solveA() {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		if (N > 1) {
			int num = 1;
			int prev = sc.nextInt();
			Boolean isInc = null;
			for (int i = 1; i < N; i++) {
				int tmp = sc.nextInt();
				if (prev < tmp) {
					if (isInc == null) {
						isInc = true;
					} else if (!isInc) {
						num++;
						isInc = null;
					}
				}
				if (prev > tmp) {
					if (isInc == null) {
						isInc = false;
					} else if (isInc) {
						num++;
						isInc = null;
					}
				}
				prev = tmp;
			}
			System.out.println(num);
		} else {
			System.out.println(1);
		}
	}
}