import java.util.Scanner;

class Main {
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		
		int n = sc.nextInt();
		int[] k = new int[n];
		int[] p = new int[n];
		
		for (int i = 0; i < n; i++){
			k[i] = sc.nextInt();
			p[i] = sc.nextInt();
		}
		
		for (int i = 0; i < n; i++){
			int res = k[i] % p[i];
			if (res == 0){
				res = p[i];
			}
			
			System.out.println(res);
		}
	}
}