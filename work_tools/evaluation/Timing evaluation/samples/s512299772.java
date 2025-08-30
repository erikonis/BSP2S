import java.util.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws java.io.IOException {
		// TODO 自動生成されたメソッド・スタブ
		Scanner sc = new Scanner(System.in);
		char bor[][] = new char[3][3];

		while (true){
			if(sc.hasNextInt())break;
			for (int i = 0; i < 3; i++) {
				bor[i] = sc.nextLine().toCharArray();
			}
//			System.out.println(bor[0]);
//			System.out.println(bor[1]);
//			System.out.println(bor[2]);
			
			char ans = 'u';
			for (int i = 0; i < 3; i++) {
				if (bor[i][0] == bor[i][1] && bor[i][0] == bor[i][2]) {
					if (bor[i][0] == 'w') {
						ans = 'w';
						break;
					} else if (bor[i][0] == 'b') {
						ans = 'b';
						break;
					} else
						continue;
				}
			}
			if (ans != 'u') {
				System.out.println(ans);
				continue;
			}

			for (int i = 0; i < 3; i++) {
				if (bor[0][i] == bor[1][i] && bor[0][i] == bor[2][i]) {
					if (bor[0][i] == 'w') {
						ans = 'w';
						break;
					} else if (bor[0][i] == 'b') {
						ans = 'b';
						break;
					} else
						continue;
				}
			}
				if (ans != 'u') {
					System.out.println(ans);
					continue;
				}
				if (bor[0][0] == bor[1][1] && bor[0][0] == bor[2][2]) {
					char j = bor[0][0];
					if (j == 'w')
						ans = 'w';
					else if (j == 'b')
						ans = 'b';
				}
				
				if (ans != 'u') {
					System.out.println(ans);
					continue;
				}
				
				if (bor[0][2] == bor[1][1] && bor[0][2] == bor[2][0]) {
					char j = bor[1][1];
					if (j == 'w')
						ans = 'w';
					else if (j == 'b')
						ans = 'b';
				}
				
				if (ans != 'u') {
					System.out.println(ans);
				}
				else System.out.println("NA");
				

			}
		}
	}