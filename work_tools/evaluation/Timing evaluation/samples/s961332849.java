import java.util.HashSet;
import java.util.Scanner;

public class Main{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		HashSet<Integer> map = new HashSet<Integer>();
		for(int i = 0; i < N; i++) {
			int A = sc.nextInt();
			if(map.contains(A)) map.remove(A);
			else map.add(A);
		}
		System.out.println(map.size());
	}
}