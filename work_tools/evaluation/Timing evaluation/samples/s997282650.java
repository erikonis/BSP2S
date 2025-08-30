import java.math.BigInteger;
import java.util.*;

public class Main {
	String s;
	int n;
	boolean []used;
	ArrayList<String[]> list, list2;
	HashMap<String, Integer> set;
	
	private void dfs(int deep){
		if(deep == n){
			StringBuilder sb1 = new StringBuilder();
			StringBuilder sb2 = new StringBuilder();
			for(int i = 0; i < n; i++){
				if(used[i]){
					sb1.append(s.charAt(i));
				}
				else{
					sb2.append(s.charAt(i));
				}
			}
			list.add(new String[]{sb1.toString(), sb2.reverse().toString()});
			return;
		}
		used[deep] = true;
		dfs(deep+1);
		used[deep] = false;
		dfs(deep+1);
	}
	private void dfs2(int deep){
		if(deep == n){
			StringBuilder sb1 = new StringBuilder();
			StringBuilder sb2 = new StringBuilder();
			for(int i = 0; i < n; i++){
				if(used[i]){
					sb1.append(s.charAt(2 * n - 1 - i));
				}
				else{
					sb2.append(s.charAt(2 * n - 1 - i));
				}
			}
			
			String s1 = sb1.reverse().toString();
			String s2 = sb2.toString();
			String s = s1 + " " + s2;
			if(set.containsKey(s)){
				set.put(s, set.get(s) + 1);
			}
			else{
				set.put(s, 1);
			}
			return;
		}
		used[deep] = true;
		dfs2(deep+1);
		used[deep] = false;
		dfs2(deep+1);
	}
	
	private void doit(){
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()){
			n = sc.nextInt();
			s = sc.next();
			used = new boolean[n];
			list = new ArrayList<>();
			dfs(0);
			used = new boolean[n];
			set = new HashMap<>();
			dfs2(0);
			long count = 0;
			for(int i = 0; i < list.size(); i++){
				String s = list.get(i)[1] + " " + list.get(i)[0];
				if(set.containsKey(s)){
					count += set.get(s);
				}
			}
			System.out.println(count);
		}
	}

	private void debug(Object... o) {
		System.out.println("debug = " + Arrays.deepToString(o));
	}

	public static void main(String[] args) {
		new Main().doit();
	}

}
