import java.util.*;
import java.util.stream.*;

class Main {
	Scanner sc;
	String S;
	int[] c;
	
	public Main() {
		sc = new Scanner(System.in);
	}
	
	private void calc() {
		S = sc.next();
		c = new int[S.length()];
		int i = 0;
		while (true) {
			int j = i;
			for (;j < S.length(); j++)
				if (S.charAt(j) == 'L') break;
			if (j == S.length()) break;
			for (int k = i; k < j; k++)
				c[j - (j-k)%2]++;
			i = j;
			for (j = i+1; j < S.length(); j++)
				if (S.charAt(j) == 'R') break;
			for (int k = i; k < j; k++)
				c[i - (k-i)%2]++;
			i = j;
		}
		out(IntStream.of(c)
					.mapToObj(n -> String.valueOf(n))
					.collect(Collectors.joining(" ")));
	}
	
	void out(Object o) {
		System.out.println(o);
		System.out.flush();
	}
	
	public static void main(String[] args) {
		Main m = new Main();
		m.calc();
	}

}