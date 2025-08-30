import java.util.*;

public class Main{
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		List<Integer> list = new ArrayList<>(n);
		for(int i = 0; i < n; i++){
			list.add(sc.nextInt());
		}
		Collections.reverse(list);
		for(int i = 0; i < list.size(); i++){
			if(i == list.size() -1){
				System.out.println(list.get(i));
			}else{
				System.out.printf("%d ",list.get(i));
			}
		}
	}
}

