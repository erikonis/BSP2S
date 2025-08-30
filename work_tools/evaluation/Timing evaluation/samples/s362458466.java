import java.util.Scanner;

class Main{
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		int n = sc.nextInt();
		
		String A = "";
		
		for(int i=0;i<n;i++)
			A = A + Integer.toString(sc.nextInt());
		
		int m = sc.nextInt();
		
		String B = "";
		
		for(int i=0;i<m;i++)
			B = B + Integer.toString(sc.nextInt());
		
		int Ans = 0;
		
		if(A.compareTo(B) < 0) Ans = 1;
		
		else Ans = 0;
		
		
		System.out.println(Ans);
	}
}
