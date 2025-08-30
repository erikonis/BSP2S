import java.util.*;

class Main {
	public static void main(String[] args) {
	
		Scanner sc = new Scanner(System.in);
		Map<String, Integer> map = new TreeMap<>();
		
		int N = sc.nextInt();
		int max = 0;
		
		for (int i=0; i<N; i++) {
			String S = sc.next();
			if (map.containsKey(S)) {
				int tmp = map.get(S) + 1;
				map.put(S, tmp);
				max = Math.max(max, tmp);
			} else {
				map.put(S, 1);
				max = Math.max(max, 1);
			}
		}
		for (String key : map.keySet()) {
			if (max == map.get(key)) {
				System.out.println(key);
			}
		}
	}
}