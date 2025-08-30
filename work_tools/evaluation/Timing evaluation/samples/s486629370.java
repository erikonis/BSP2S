import java.util.Scanner;

public class Main{
	public static void main(String[] args) {
		Scanner stdIn = new Scanner(System.in);
		int x = stdIn.nextInt();
        int a = 0, b, p, i, t;
		for(b = 1;b <= x;b++) {
			t = b;
			for(p = 2;p <= x;p++) {
				t *= b;
				if(t <=x && a < t) a = t;
			}
		}
		if(x == 1) a = 1;
		System.out.print(a);
	}
}
