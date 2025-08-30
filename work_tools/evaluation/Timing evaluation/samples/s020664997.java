import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
	
	public static void main(String[] args) {
		final Scanner sc = new Scanner(System.in);

		while (true) {
			final int n = Integer.parseInt(sc.nextLine());
			
			if(n == 0){
				break;
			}
			
			int score = 0;
			int prev_suc_state = 0;
			int cur_state = 0;
			
			StringTokenizer token = new StringTokenizer(sc.nextLine());
			while(token.hasMoreTokens()){
				char[] input = token.nextToken().toCharArray();
				//System.out.println(Arrays.toString(input));
				
				boolean is_left = input[0] == 'l';
				boolean is_up = input[1] == 'u';
				
				cur_state += (is_up ? 1 : -1) * (is_left ? 10 : 1);
				
				if(cur_state == 10 || cur_state == 01){
					continue;
				}else if(cur_state == prev_suc_state){
					continue;
				}else{
					prev_suc_state = cur_state;
					score++;
				}
			}
			
			System.out.println(score);
		}
	}
}