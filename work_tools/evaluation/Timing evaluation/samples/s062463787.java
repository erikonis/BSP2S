
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int N = scan.nextInt();
		String[] C = new String[N];
		for(int i=0; i<N; i++) {
			C[i] = scan.next();
		}
		scan.close();
		String[] bubbleSortedC = C.clone();
		String[] SelectionSortedC = C.clone();

		BubbleSort(bubbleSortedC, N);
		SelectionSort(SelectionSortedC, N);

		trace(bubbleSortedC);
		System.out.println("Stable");	//バブルソートは安定
		trace(SelectionSortedC);

		for(int i=0; i<C.length; i++) {
			if(bubbleSortedC[i] == SelectionSortedC[i]) {
				continue;
			} else {
				//バブルソートと異なるので安定でない
				System.out.println("Not stable");
				return;
			}
		}
		System.out.println("Stable");	//バブルソートと一致したので安定

	}

	private static void SelectionSort(String[] C, int N) {
		for(int i=0; i<N; i++) {
			int minj = i;
			for(int j=i; j<N; j++) {

				if(Integer.parseInt(C[j].substring(1)) < Integer.parseInt(C[minj].substring(1))) {
					minj = j;
				}
			}
			String t = C[i];
			C[i] = C[minj];
			C[minj] = t;
		}
	}

	private static void BubbleSort(String[] C, int N) {
		for(int i=0; i<N; i++) {
			for(int j=N-1; j>i; j--) {
				if(Integer.parseInt(C[j].substring(1)) < Integer.parseInt(C[j-1].substring(1))) {
					String t = C[j];
					C[j] = C[j-1];
					C[j-1] = t;
				}

			}
		}
	}

	private static void trace(String[] C) {
		for(int i=0; i<C.length; i++) {
			if(i != 0) {
				System.out.print(" ");
			}
			System.out.print(C[i]);
		}
		System.out.println();
	}
}

