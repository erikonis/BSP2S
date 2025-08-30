import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Scanner;

public class Main {

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] arg) {
		ArrayList<Integer> primes = primes(110000);
		while (true) {
			int N = sc.nextInt();
			int P = sc.nextInt();
			if (N == -1) break;
			int pos = 0;
			while (primes.get(pos) <= N) {
				++pos;
			}
			ArrayList<Integer> sum = new ArrayList<Integer>();
			for (int i = 0; i < 100; ++i) {
				for (int j = 0; j <= i; ++j) {
					sum.add(primes.get(pos + i) + primes.get(pos + j));
				}
			}
			Collections.sort(sum);
			System.out.println(sum.get(P - 1));
		}
	}

	static ArrayList<Integer> primes(int max) {
		if (max < 2) {
			return new ArrayList<Integer>();
		}
		BitSet primeSet = new BitSet(max / 2);
		primeSet.set(1, max / 2);
		int limit = (int) Math.sqrt(max);
		for (int i = 3; i <= limit; i += 2) {
			if (!primeSet.get(i / 2)) {
				continue;
			}
			for (int j = i * i; j < max; j += i * 2) {
				primeSet.clear(j / 2);
			}
		}
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(2);
		for (int i = primeSet.nextSetBit(0); i >= 0; i = primeSet.nextSetBit(i + 1)) {
			list.add(i * 2 + 1);
		}
		return list;
	}

}