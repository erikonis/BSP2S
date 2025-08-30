
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();

		int a[] = new int[100001];

		for(int i = 0; i < n; i++){
			a[i] = sc.nextInt();
		}

		int SEG_SIZE = 100;
		int seg[] = new int[a.length/SEG_SIZE];
		int gcdInSegWithout[] = new int[100001];

		for(int i = 0; i < n; i += SEG_SIZE){
			int tmpgcd = a[i];

			int segID = i / SEG_SIZE;
			for(int j = 0; j < SEG_SIZE; j++){
				if(segID*SEG_SIZE + j >= n){
					break;
				}


				tmpgcd = gcd(a[segID*SEG_SIZE + j], tmpgcd);
			}
			seg[segID] = tmpgcd;
		}
//
//		for(int i = 0; i < 10; i++){
//			System.out.print(seg[i]+" ");
//		}
//		System.out.println();

		for(int i = 0; i < n; i ++){
			int tmpgcd = 0;

			int segID = i / SEG_SIZE;
			for(int j = 0; j < SEG_SIZE; j++){
				if(segID*SEG_SIZE + j >= n){
					break;
				}

				if(segID*SEG_SIZE + j == i){
					continue;
				}

				if(tmpgcd == 0){
					tmpgcd = a[segID*SEG_SIZE + j];
				}
				else {
					tmpgcd = gcd(a[segID*SEG_SIZE + j], tmpgcd);
				}
			}
			gcdInSegWithout[i] = tmpgcd;
		}
//		for(int i = 0; i < 10; i++){
//			System.out.print(gcdInSegWithout[i]+" ");
//		}
//		System.out.println();

		int result = 0;
		for(int i = 0; i < n; i++){

			int segID = i / SEG_SIZE;
			int tmpgcd = gcdInSegWithout[i];

			for(int j = 0; j < seg.length; j++){
				if(j == segID || seg[j] == 0){
					continue;
				}

				if(tmpgcd == 0){
					tmpgcd = seg[j];
				}
				else {
					tmpgcd = gcd(tmpgcd, seg[j]);
				}
			}

			result = Math.max(result, tmpgcd);
		}

		System.out.println(result);

	}

	static int gcd(int a, int b){
    	int tmp;

    	while (a % b != 0)
    	{
    		tmp = b;
    		b = a % b;
    		a = tmp;
    	}
    	return b;
    }
}
