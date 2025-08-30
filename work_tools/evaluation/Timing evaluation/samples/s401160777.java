import java.util.Scanner;

public class Main {
	private static final String ALF = "abcdefghijklmnopqrstuvwxyz"; 
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		long dog = sc.nextLong();

		String dogName = convertToName(dog);
		System.out.println(dogName.toString());
	}

	static String convertToName(long dog) {
		//文字数を特定する
		int digitNum = 1;
		long[] digitParts = new long[15];
		digitParts[0] = 26;
		long digitSum = 26;
		for (int i = 0; digitSum < dog; i++) {
			digitParts[i + 1] = (long) (Math.pow(ALF.length(), ++digitNum));
			digitSum += digitParts[i + 1];
		}
		
		int[] res = new int[digitNum];
		
		/// 与えられた数から桁数-1までの組み合わせ分だけ引く
		for (int i = 0; i < digitNum - 1; i++) {
			dog -= digitParts[i];
		}
		
		// 計算した数を26進数に変換する
		//0始まりとする
		dog --;
		for(int i = digitNum - 1; i > 0; i--) {
			int rankNum = 0;
			while(Math.pow(ALF.length(), i) * (rankNum + 1) <= dog) {
				rankNum++;
			}
			res[digitNum - 1 - i] = rankNum;
			dog -= Math.pow(ALF.length(), i) * rankNum;
		}
		res[digitNum - 1] = (int) dog;
		
		// 26進数を文字に変換する
		StringBuffer dogName = new StringBuffer();
		for (int i = 0; i < res.length; i++) {
			dogName = dogName.append(ALF.charAt(res[i]));
		}
		return dogName.toString();
	}
}
