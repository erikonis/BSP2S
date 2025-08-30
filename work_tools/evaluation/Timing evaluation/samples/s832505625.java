import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
	
	public static int[][] eat_type = new int[][]{{0, 1, 2}, {0}, {1}, {2}};
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		final int N = sc.nextInt();
		final int K = sc.nextInt();
		
		int[] pasta = new int[N];
		
		for(int i = 0; i < K; i++){
			pasta[sc.nextInt() - 1] = sc.nextInt();
		}
		
		int[] cur = new int[27];
		int[] next = new int[27];
		
		for(int t_eat : eat_type[pasta[2]]){
			for(int p_eat : eat_type[pasta[1]]){
				for(int pp_eat : eat_type[pasta[0]]){
					final int number = pp_eat * 9 + p_eat * 3 + t_eat;
					
					cur[number]++;
				}
			}
		}
		
		for(int day = 3; day < N; day++){
			Arrays.fill(next, 0);
			
			for(int eat : eat_type[pasta[day]]){
				for(int number = 0; number < 27; number++){
					final int t_eat = number % 3;
					final int p_eat = number / 3 % 3;
					final int pp_eat = number / 9 % 3;
					
					if(t_eat == p_eat && p_eat == pp_eat){
						continue;
					}else if(eat == t_eat && eat == p_eat){
						continue;
					}
					
					next[p_eat * 9 + t_eat * 3 + eat] += cur[number];
					next[p_eat * 9 + t_eat * 3 + eat] %= 10000;
				}
			}
			
			{
				int[] tmp = cur;
				cur = next;
				next = tmp;
			}
		}
		
		int sum = 0;
		for(int number = 0; number < 27; number++){
			final int t_eat = number % 3;
			final int p_eat = number / 3 % 3;
			final int pp_eat = number / 9 % 3;
			
			if(t_eat == p_eat && p_eat == pp_eat){
				continue;
			}
			
			sum += cur[number];
			sum %= 10000;
		}
		
		System.out.println(sum);
	}

}