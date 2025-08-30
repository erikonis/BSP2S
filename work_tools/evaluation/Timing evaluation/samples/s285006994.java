
import java.util.Scanner;

public class Main {

    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
	long n = Long.parseLong(scan.next());
	long s = Long.parseLong(scan.next());

	long ans = 0;
	int rootN = (int)(Math.sqrt(n));
	if(n == s) {
	    ans = n+1;
	} else {
	    for (int b = 2; b <= rootN; b++) {
		if(f(b, n) == s) {
		    ans = b;
		    break;
		}
	    }
	    if(ans == 0) {
		for (int p = rootN; p > 0; p--) {
		    if((n-s) % p == 0) {
			long b = (n-s)/p+1;
			if(b > 1 && f(b, n) == s) {
			    ans = b;
			    break;
			}
		    }
		}
	    }
	}
	
	System.out.println(ans > 0 ? ans : -1);
	scan.close();
    }

    private static long f(long b, long n) {
	long result = 0;
	while(n / b > 0) {
	    result += n % b;
	    n = n/b;
	}
	result += n;
	return result;
    }
}
