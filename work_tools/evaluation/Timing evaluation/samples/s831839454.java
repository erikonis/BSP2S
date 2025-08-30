import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

	public static int minium_win(char input[], int start, int end){
		if(input[start] != '[' || input[end] != ']'){
			return Integer.MIN_VALUE;
		}
		
		int c_lv = 0;
		int c_start = -1;
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int pos = start + 1; pos < end; pos++){
			if(input[pos] == '['){
				if(c_lv == 0){
					c_start = pos;
				}
				
				c_lv++;
			}else if(input[pos] == ']'){
				c_lv--;
				
				if(c_lv == 0){
					if(input[c_start + 1] == '[' && input[pos - 1] == ']'){
						list.add(minium_win(input, c_start, pos));
					}else{
						int num = 0;
						for(int keta = c_start + 1; keta < pos; keta++){
							num *= 10;
							num += input[keta] - '0';
						}
						
						list.add(num / 2 + 1);
					}
					
				}
			}
		}
		
		Collections.sort(list);
		//System.out.println(list);
		
		int ret = 0;
		for(int i = 0; i < list.size() / 2 + 1; i++){
			ret += list.get(i);
		}
		
		return ret;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		final int N = sc.nextInt();
		
		for(int tt = 0; tt < N; tt++){
			char[] input = sc.next().toCharArray();
			
			//System.out.println(Arrays.toString(input));
			
			System.out.println(minium_win(input, 0, input.length - 1));
		}
	}
}