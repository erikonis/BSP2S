import java.util.Scanner;

public class Main {
	public static void main(String[] args)  {
		Scanner sc = new Scanner(System.in);
		int H = sc.nextInt();
		int W = sc.nextInt();
		String[][] snake = new String[H][W];
		char a = 'A';
		for(int i = 0; i < H; i++) {
			for(int j = 0; j < W; j++) {
				snake[i][j] = sc.next();
				if(snake[i][j].equals("snuke")) {
					a += j;
					i++;
					System.out.println(a + "" + i );
					return;
				}
			}
		}
	}
}