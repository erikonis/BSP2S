import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
	static final int MAX_NUM = 76543210;
	static final int NUM_BOARD = 8;
	char[] ans;

	void run() {
		Scanner scan = new Scanner(System.in);
		ans = new char[MAX_NUM + 1];
		bfs();
		while (scan.hasNext()) {
			char[] pazzle = new char[NUM_BOARD];
			for (int i = 0; i < NUM_BOARD; i++) {
				pazzle[i] = (char)scan.nextInt();
			}
			System.out.println((int)ans[convertArrayToNumber(pazzle)]);
		}
	}
	
	void bfs() {
		// ?§????(??\???)????????????(01234567)??§??????????????????????§??????§?????????
		// ????????????????????????????????¨???????§???????????????¢?????¨??????????????????
		Queue<char[]> que = new LinkedList<char[]>();
		que.add(new char[] { 0, 1, 2, 3, 4, 5, 6, 7 });
		while (!que.isEmpty()) {
			int size = que.size();
			for (int i = 0; i < size; i++) {
				char[] p = que.poll();
				int num = convertArrayToNumber(p);
				int index = getZeroOfIndex(p);
				int[] d = { -4, -1, 1, 4 };
				for (int j = 0; j < d.length; j++) {
					int ni = index + d[j];
					if (ni < 0 || ni >= NUM_BOARD) continue;
					if (index == 4 && d[j] == -1)  continue;
					if (index == 3 && d[j] == 1)   continue;

					char[] np = p.clone();
					swap(np, ni, index);
					int n = convertArrayToNumber(np);
					if (ans[n] != 0)
						continue;
					if (ans[n] == 0 && n == 1234567)
						continue;
					ans[n] = (char)(ans[num] + 1);
					que.add(np);
				}
			}
		}
	}

	void swap(char[] array, int a, int b) {
		char tmp = array[a];
		array[a] = array[b];
		array[b] = tmp;
	}

	int convertArrayToNumber(char[] array) {
		int sum = 0;
		for (int i = 1; i <= NUM_BOARD; i++) {
			sum += array[i - 1] * Math.pow(10, NUM_BOARD - i);
		}
		return sum;
	}

	int getZeroOfIndex(char[] array) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == 0)
				return i;
		}
		return -1;
	}

	public static void main(String[] args) {
		new Main().run();
	}
}