import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {
	static class Elem implements Comparable<Elem> {
		long val;
		int prevIndex;

		Elem(long val) {
			this.val = val;
			this.prevIndex = -1;
		}

		@Override
		public int compareTo(Elem o) {
			if (val < o.val) {
				return 1;
			} else if (val > o.val) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		InputStreamReader reader = new InputStreamReader(System.in, StandardCharsets.UTF_8);
		BufferedReader in = new BufferedReader(reader);

		int N = Integer.parseInt(in.readLine());
		long[] A = new long[N];
		for (int i = 0; i < N; ++i) {
			A[i] = Long.parseLong(in.readLine());
		}
		List<Long> list = new ArrayList<>();
		Map<Long, Integer> nums = new HashMap<>();
		for (int i = 0; i < N; ++i) {
			int index = Collections.binarySearch(list, A[i]);

			if (index >= 0) {
				if (index > 0) {
					nums.put(list.get(index - 1), nums.get(list.get(index - 1)) - 1);
					nums.put(list.get(index), nums.get(list.get(index)) + 1);
					if (nums.get(list.get(index - 1)) == 0) {
						nums.remove(list.get(index - 1));
						list.remove(index - 1);
					}
				} else {
					nums.put(list.get(index), nums.get(list.get(index)) + 1);
				}
			} else {
				// 挿入
				int j = (index + 1) * -1;
				if (j > 0) {
					list.add(j, A[i]);
					nums.put(A[i], 1);
					nums.put(list.get(j - 1), nums.get(list.get(j - 1)) - 1);
					if (nums.get(list.get(j - 1)) == 0) {
						nums.remove(list.get(j - 1));
						list.remove(j - 1);
					}
				} else {
					nums.put(A[i], 1);
					list.add(j, A[i]);
				}
			}
		}

		int result = 0;
		for(Long key: nums.keySet()) {
			result += nums.get(key);
		}
		System.out.println(result);
	}
}