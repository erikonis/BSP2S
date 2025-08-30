import java.util.Scanner;

public class Main {
	Scanner sc = new Scanner(System.in);
	int x;
	int[] kionsa = {1,2,3,4,5,6,7};
	public void nyuryoku(){
		for(x=0;x<=6;x++){
			int saiko = sc.nextInt();
			int saitei = sc.nextInt();
			kionsa[x] = saiko-saitei;
		}
	}
	
	public void nyuuryoku2(){
		for(x=0;x<=6;x++){
			System.out.println(kionsa[x]);
		}
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		
		Main hai =new Main();
		hai.nyuryoku();
		hai.nyuuryoku2();

	}

}