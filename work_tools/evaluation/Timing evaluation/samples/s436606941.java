import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		while (true) {
			final int n = sc.nextInt();
			final int m = sc.nextInt();
			
			if(n == 0 && m == 0){
				break;
			}
			
			boolean[][] is_friend = new boolean[n][n];
			
			for(int i = 0; i < m; i++){
				int one = sc.nextInt() - 1;
				int two = sc.nextInt() - 1;
				
				is_friend[one][two] = is_friend[two][one] = true;
			}
			
			boolean[] one_visited = new boolean[n];
			boolean[] two_visited = new boolean[n];
			
			for(int i = 1; i < n; i++){
				if(!one_visited[i] && is_friend[0][i]){
					one_visited[i] = true;
					two_visited[i] = true;
				}
			}
			
			for(int i = 1; i < n; i++){
				if(one_visited[i]){
					for(int j = 1; j < n; j++){
						if(!two_visited[j] && is_friend[i][j]){
							two_visited[j] = true;
						}
					}
				}
			}
			
			int count = 0;
			for(boolean b : two_visited){
				if(b){
					count++;
				}
			}
			
			System.out.println(count);
		}
	}

}