import java.util.Scanner;

public class Main {
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		int M = in.nextInt();
		int count[] = new int[11];
		while(N != 0 || M != 0){
			for(int i = 0;i <= 10;i++) count[i] = 0;
			int countP = 0;
			for(int i = 0;i < N;i++){
				int D = in.nextInt();
				int P = in.nextInt();
				count[P] = count[P] + D;
			}
			for(int i = 0;i < M;i++){
				int n = 0;
				for(int j = 10;j > 0;j--){
					n = j;
					if(count[j] > 0) break;
				}
				if(count[n] > 0){
					if(i + count[n] < M){
						i = i + count[n] - 1;
						count[n] = 0;
					}else {
						count[n] = count[n] - (M-i);
						i = M;
					}
				}
				else break;
			}
			for(int i = 1;i < 11;i++) {
				if(count[i] > 0){
					countP = countP + i*count[i];
				}
			}
			System.out.println(countP);
			N = in.nextInt();
			M = in.nextInt();
		}
	}
}