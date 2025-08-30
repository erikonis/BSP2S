import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int m = sc.nextInt();
		
		List<List<Integer>> graph = new ArrayList<List<Integer>>();
		for (int i = 0; i < n; i++) {
			graph.add(new ArrayList<>());
		}
		
		for (int i = 0; i < m; i++) {
			int a = sc.nextInt();
			int b = sc.nextInt();
			a--;
			b--;
			graph.get(a).add(b);
			graph.get(b).add(a);
		}
		
		int[] guide = new int[n];
		int[] dist = new int[n];
		Arrays.fill(dist, n);
		
		Queue<Integer> que = new ArrayDeque<Integer>();
		for (int i = 0; i < graph.get(0).size(); i++) {
			int nv = graph.get(0).get(i);
			que.add(nv);
			guide[nv] = 1;
			dist[nv] = 1;
		}
		
		while (!que.isEmpty()) {
			int v = que.poll();
			
			List<Integer> list = graph.get(v);
			int size = list.size();
			for (int i = 0; i < size; i++) {
				int nv = list.get(i);
				if (dist[v] + 1 < dist[nv]) {
					dist[nv] = dist[v] + 1;
					que.add(nv);
					guide[nv] = v + 1;
				}
			}
		}
		
		System.out.println("Yes");
		for (int i = 1; i < guide.length; i++) {
			System.out.println(guide[i]);
		}
	}
}
