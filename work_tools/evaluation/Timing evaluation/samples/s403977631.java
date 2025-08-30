import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		long n = sc.nextLong();
		sc.close();
		int ans = 0;
		long [] a = new long[100];
		if (n ==1) {
			System.out.println(ans);
			return;
		}
		a = soinsubunakai(n);

		int k = 1;
		long pa = 0;
		int j = 1;
		for (int i=0; a[i]!=0; i++, j++) {
			if (pa != a[i]) {
				k = 1;
				j = 1;
			}
			if (j == k) {
				ans++;
				k++;
				j = 0;
			}
			pa = a[i];
		}
		System.out.println(ans);
	}

	private static long[] soinsubunakai(long num)
	{
		long [] a = new long[100];
		int index = 0;
        boolean endFlag = false;
        long i = 2;
        long x = num;
        for (;i*i <= x;) {
            if (x % i == 0) { // 最小の因数を求める
                //System.out.print(i);
            	a[index] = i;
            	index++;
                if (x == i) {
                	endFlag = true;
                	break;
                }
                //System.out.print(" * "); // xと因数が同値になったら＊の挿入をやめる
                x /= i; // 最小の因数で割った数を代入する
            } else {
                i++;
            }
        }
        if (!endFlag) {
            //System.out.println(x);
        	a[index] = x;
            //System.out.print(i);
        }
		return a;
	}
}
