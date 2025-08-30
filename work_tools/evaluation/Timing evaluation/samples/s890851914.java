import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String input = br.readLine();
		long n = Long.parseLong(input);
		int len = input.length();
		int max = 0;

		if(len == 1){
			max = (int)n;
		}
		else {
			int numOf9 = 0;

			for(int i = 1; i < len; i++){
				if(input.charAt(i) == '9'){
					numOf9++;
				}
			}

			if(numOf9 == len - 1){
				max = (int)(input.charAt(0) - '0') + 9 * (len - 1);
			}
			else {
				max = (int)(input.charAt(0) - '0') - 1 + 9 * (len - 1);
			}

		}
//		for(int i = 1; i <= n; i++){
//			int num = i;
//
//			int tmpSum = 0;
//			while(num > 0){
//				tmpSum += num%10;
//				num /= 10;
//			}
//
//			max = Math.max(max, tmpSum);
//		}

		System.out.println(max);
	}

}
