import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		//List<Integer> check=new ArrayList<Integer>();
		//array = new int[h+1][w+1];
		//List<String> list= new ArrayList<String>(Arrays.asList(s.split("")));
		//Collections.reverse(list);
		int n = sc.nextInt();
		for(int i=1;10>i;i++) {
			for(int j=1;10>j;j++) {
				if(n==i*j) {
					System.out.println("Yes");
					System.exit(0);
				}
			}
		}
		System.out.println("No");
	}

}
