import java.util.Scanner;

public class Main {
    static int H;
    static int[] A;

    static void maxHeapify(int i) {
        int l = 2 * i;
        int r = 2 * i + 1;
        int largest;
        if (l <= H && A[l] > A[i]) {
            largest = l;
        } else {
            largest = i;
        }
        if (r <= H && A[r] > A[largest]) {
            largest = r;
        }
        if (largest != i) {
            int tmp = A[i];
            A[i] = A[largest];
            A[largest] = tmp;
            maxHeapify(largest);
        }
    }

    static void buildMaxHeap() {
        for (int i = H / 2; i >= 1; i--) {
            maxHeapify(i);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        H = sc.nextInt();
        A = new int[H + 1];
        for (int i = 1; i <= H; i++) {
            A[i] = sc.nextInt();
        }
        buildMaxHeap();
        for (int i = 1; i <= H; i++) {
            System.out.print(" " + A[i]);
        }
        System.out.println();
    }
}