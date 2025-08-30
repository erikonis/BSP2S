import java.util.Arrays;
import java.util.Scanner;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		while(cin.hasNext()){
			int n = cin.nextInt();
			int[] a = new int[n];
			for(int i = 0; i < n;i++){
				a[i]=cin.nextInt();
			}
			Arrays.sort(a);
			int[] sum = new int[n+1];
			for(int i = 0;  i< n;i++){
				sum[i+1] = sum[i]+a[i];
			}
			int sumsum=0;
			for(int i = 0; i < n+1;i++){
				sumsum+=sum[i];
			}
			System.out.println(sumsum);
		}

	}

}