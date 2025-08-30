import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;


public class Main {

	public static class Thief implements Comparable<Thief>{
			int from, cur, weight, bit;
			double time;
			
			public Thief(int bit, int from, int cur, int weight, double time) {
				super();
				this.bit = bit;
				this.from = from;
				this.cur = cur;
				this.weight = weight;
				this.time = time;
			}

			@Override
			public int compareTo(Thief arg0) {
				if(this.time < arg0.time){
					return -1;
				}else if(this.time > arg0.time){
					return 1;
				}else{
					return 0;
				}
			}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		final int n = sc.nextInt();
		
		final int[] number = new int[n];
		final int[] o_dist = new int[n];
		final int[] count = new int[n];
		
		for(int i = 0; i < n; i++){
			number[i] = sc.nextInt();
			o_dist[i] = sc.nextInt();
			count[i] = sc.nextInt();
		}
		
		int[][] dist = new int[n][n];
		
		for(int i = 0; i < n; i++){
			for(int j = i + 1; j < n; j++){
				if(i == 0 && j == 0){
					continue;
				}
				
				dist[i][j] = dist[j][i] = Math.abs(o_dist[i] - o_dist[j]);
			}
		}
		
		int[][] visited = new int[n][1 << n];
		for(int i = 0; i < n; i++){
			Arrays.fill(visited[i], Integer.MIN_VALUE);
		}
		//boolean[][] inqueue = new boolean[n][1 << n];
		
		PriorityQueue<Thief> queue = new PriorityQueue<Thief>();
		for(int i = 0; i < n; i++){
			queue.add(new Thief(0, -1, i, 0, 0));
		}
		
		while(!queue.isEmpty()){
			Thief thief = queue.poll();
			
			//System.out.println(number[thief.cur] + " " + Integer.toBinaryString(thief.bit) + " " + thief.time);
			
			if(visited[thief.cur][thief.bit] != Integer.MIN_VALUE){
				continue;
			}
			visited[thief.cur][thief.bit] = thief.from;
			//inqueue[thief.cur][thief.bit] = false;
			
			final int cur_weight = thief.weight + count[thief.cur] * 20;
			final int bit = thief.bit | (1 << thief.cur);
			
			if(bit == ((1 << n) - 1)){
				LinkedList<Integer> list = new LinkedList<Integer>();
				
				int cur = thief.cur;
				int c_bit = thief.bit;
				while(true){
					//System.out.println(cur + " " + c_bit + " " + visited[cur][c_bit]);
					//System.out.println(c_bit + " " + cur);
					if(cur == -1){
						break;
					}
					list.addFirst(number[cur]);
					
					if(visited[cur][c_bit] == Integer.MIN_VALUE){
						break;
					}
					
					//int old = c_bit;
					cur = visited[cur][c_bit];
					c_bit &= (((1 << n) - 1) - (1 << cur));
					
				}
				//System.out.println(c_bit);
				boolean first = true;
				for(int num : list){
					System.out.print((first ? "" : " ") + num);
					first = false;
				}
				System.out.println();
				
				break;
			}
			
			for(int next = 0; next < n; next++){
				//System.out.println(next);
				if((bit & (1 << next)) != 0){
					continue;
				}
				//System.out.println(next + "!");
				if(visited[next][bit] != Integer.MIN_VALUE){
					continue;
				}
				
				final double length = dist[thief.cur][next];
				final double plus = length / (2000.0 / (70.0 * cur_weight));
				//a
				//System.out.println("!" + number[thief.cur] + " " + number[next] + " " + (thief.time + plus));
				
				queue.add(new Thief(bit, thief.cur, next, cur_weight, thief.time + plus));
			}
		}
		
		
	}

}