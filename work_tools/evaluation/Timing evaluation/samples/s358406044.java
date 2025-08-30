
import java.util.Arrays;
import java.util.Scanner;

public class Main{
	public static void main(String[]args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] hen = new int[3];
		for(int j=0; j<n;j++) {
		for(int i=0; i<3;i++)hen[i]=sc.nextInt();
		Arrays.sort(hen);
		if(hen[0]*hen[0]+hen[1]*hen[1]==hen[2]*hen[2]) {
			System.out.println("YES");
		}else {
			System.out.println("NO");
		}
	}

}
}
