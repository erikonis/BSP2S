import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String s = sc.next();
		int n = s.length();
		SortedSet<String> ss = new TreeSet<String>();
		for (int i=0;i<n;i++) {
			for (int j=i;j<n && j<i+5; j++) {
				ss.add(s.substring(i, j+1));
			}
		}
		
		int k = sc.nextInt();
		System.out.println(ss.toArray()[k-1]);
	}

}
