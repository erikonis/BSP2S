import java.util.Scanner;

public class Main {
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);

		int n = sc.nextInt();
		String s = sc.next();
		String ss[] = s.split("");
		int count = 1;
		for(int i=0; i<n; i++) {
			if(i!=0) {
				if(!ss[i].equals(ss[i-1])) {
					count++;
				}
			}
		}
		System.out.print(count);

	}

}
