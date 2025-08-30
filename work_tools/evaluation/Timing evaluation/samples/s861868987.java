import java.util.Scanner;

class Main {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int[] A = new int[N];
		for(int i=0; i<N; i++){
			A[i]  = sc.nextInt();
		}
		
		int temp1 = A[0];
		int temp2 = 0;
		int count = 1;
		for(int i=1; i<N; i++){
			if(temp2==-1){
				if(temp1<A[i]){
					temp2=0;
					temp1=A[i];
					count++;
				}else{
					temp1=A[i];
				}
				
			}else if(temp2==1){
				if(temp1>A[i]){
					temp2=0;
					temp1=A[i];
					count++;
				}else{
					temp1=A[i];
				}
			}else{
				if(temp1>A[i]){
					temp2=-1;
					temp1=A[i];
				}else if(temp1<A[i]){
					temp2=1;
					temp1=A[i];
				}else{
					temp1=A[i];
				}
			}
		}
		
		System.out.println(count);

	}

}
