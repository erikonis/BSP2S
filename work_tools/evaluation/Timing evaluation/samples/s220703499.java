import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {


	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

		//get
		int companyXFeeRate = Integer.parseInt(bufferedReader.readLine());
		int companyYBase = Integer.parseInt(bufferedReader.readLine());
		int companyYLimit = Integer.parseInt(bufferedReader.readLine());
		int companyYAdd = Integer.parseInt(bufferedReader.readLine());
		int usedWater = Integer.parseInt(bufferedReader.readLine());

		int companyXFee = companyXFeeRate * usedWater;
		int companyYFee = 0;

		if(companyYLimit < usedWater) {
			int over = usedWater - companyYLimit;
			companyYFee = companyYBase + companyYAdd * over;
		} else {
			companyYFee = companyYBase;
		}

		System.out.println(companyXFee < companyYFee ? companyXFee : companyYFee);
	}

	public static String getStringCharAt(String str, int position) {
		return String.valueOf(str.charAt(position));
	}

	public static String[] getStringCharArr(String str) {

		String[] arrResult = new String[str.length()];

		for(int i = 0; i < str.length(); i++) arrResult[i] = getStringCharAt(str, i);

		return arrResult;
	}

	public static boolean isInnerPosition(int x1, int x2, int searchPosition) {
		return x1 <= searchPosition && searchPosition <= x2;
	}

}