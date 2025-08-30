
import java.util.*;
import java.io.*;
import java.math.BigInteger;

import static java.util.Arrays.*;
import static java.util.Collections.*;
import static java.lang.Math.*;

public class Main {

	int INF = 1 << 28;
	//long INF = 1L << 62;
	double EPS = 1e-10;

	void run() {
		Scanner sc = new Scanner(System.in);
		for(;;) {
			int n = sc.nextInt();
			if(n == 0) break;
			BigInteger[] d = new BigInteger[n], v = new BigInteger[n];
			d[0] = new BigInteger(sc.next()); v[0] = new BigInteger(sc.next());
			
			BigInteger div = gcd(d[0], v[0]);
			d[0] = d[0].divide(div); v[0] = v[0].divide(div);
			
			BigInteger nume = new BigInteger("" + d[0]), deno = new BigInteger("" + v[0]);
			for(int i=1;i<n;i++) {
				d[i] = new BigInteger(sc.next()); v[i] = new BigInteger(sc.next());
				div = gcd(d[i], v[i]);
				d[i] = d[i].divide(div); v[i] = v[i].divide(div);
				nume = lcm(nume, d[i]);
				deno = gcd(deno, v[i]);
			}
			for(int i=0;i<n;i++) {
				System.out.println(nume.divide(d[i]).multiply(v[i]).divide(deno) );				
			}
		}
	}
	
	BigInteger lcm(BigInteger x, BigInteger y) {
		return x.divide(gcd(x, y)).multiply(y);
	}
	
	BigInteger gcd(BigInteger x, BigInteger y) {
		if(y.equals(new BigInteger("0"))) return x;
		return gcd(y, x.divideAndRemainder(y)[1]);
	}

	void debug(Object... os) {
		System.err.println(Arrays.deepToString(os));
	}

	public static void main(String[] args) {
		new Main().run();
	}
}