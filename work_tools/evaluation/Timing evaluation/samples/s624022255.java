import java.util.Scanner;
public class Main{
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		while(scan.hasNext()){
			int n = scan.nextInt();
			if(n == 0){
				break;
			}
			int[][] s = new int[n+1][4];
			for(int i = 0;i < n+1;i++){
				for(int j = 0;j < 4;j++){
					s[i][j] = scan.nextInt();
				}
			}
			int[] a = {4,9,4};
			boolean sw = true;
			int ca;
			for(int i = 0;i < n;i++){
				ca = 0;
				for(int j = 1;j < 4;j++){
					if(s[i][j] > s[n][j-1]){
						break;
					}
					ca += s[i][j]*a[j-1];
					if(j == 3 && ca <= s[n][3]){
						System.out.println(s[i][0]);
						sw = false;
					}
				}
			}
			if(sw){
				System.out.println("NA");
			}
		}
	}
}