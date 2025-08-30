import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

class Main {
	Scanner sc = new Scanner(System.in);

	public void run() {
		while(sc.hasNext()){
			BigDecimal a=sc.nextBigDecimal();
			BigDecimal b=sc.nextBigDecimal();
			BigDecimal r=a.divide(b,MathContext.DECIMAL128);
			int n=sc.nextInt();
			int sum=0;
			for(int i=0;i<n;i++){
				r=r.multiply(BigDecimal.TEN);
				sum+=r.remainder(BigDecimal.TEN).intValue();
			}
			ln(sum);
		}
	}

	public static void main(String[] args) {
		new Main().run();
	}

	public static void pr(Object o) {
		System.out.print(o);
	}

	public static void ln(Object o) {
		System.out.println(o);
	}

	public static void ln() {
		System.out.println();
	}
}