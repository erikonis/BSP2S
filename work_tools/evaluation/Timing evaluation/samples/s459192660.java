import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int[] num = new int[10000000];
		int i = 0;
		while ( sc.hasNext() ) {
			String[] line = sc.nextLine().split(",");
			int n = Integer.parseInt(line[0]);
			double w = Double.parseDouble(line[1]) , h = Double.parseDouble(line[2]);
			if ( 25 <= ( w / ( h * h ) ) )
				num[i++] = n;
		}
		for ( int j = 0; num[j] != 0; j++ )
			System.out.println(num[j]);
	}
}