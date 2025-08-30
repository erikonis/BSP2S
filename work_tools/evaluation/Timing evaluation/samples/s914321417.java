import java.util.Scanner;

public class Main{
	public static void main(String[] args) {
		Scanner src = new Scanner(System.in);

		//配列array
		int n = src.nextInt();
		int[] array = new int[n];
		for (int i=0; i<n; i++) 
			array[i] = src.nextInt();
		
		//配列array2
		int m = src.nextInt();
		int[] array2 = new int[m];
		for (int i=0; i<m; i++) 
			array2[i] = src.nextInt();
		
		for (int i=0; i<Math.min(n,m); i++) {

			if (array[i] < array2[i]) {
				System.out.println(1); return;

			}else if (array[i] > array2[i]) {
				System.out.println(0); return;
			}
		}
		System.out.println(array.length<array2.length?1:0);
	}
}
