import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] P = new int[N];
        int[] Q = new int[N];
        for (int i = 0; i < N; i++) {
            P[i] = sc.nextInt();
        }
        for (int i = 0; i < N; i++) {
            Q[i] = sc.nextInt();
        }
        sc.close();

        List<int[]> permutations = generatePermutations(N);

        int a = -1;
        int b = -1;

        for (int i = 0; i < permutations.size(); i++) {
            int[] currentPerm = permutations.get(i);
            if (Arrays.equals(P, currentPerm)) {
                a = i + 1;
            }
            if (Arrays.equals(Q, currentPerm)) {
                b = i + 1;
            }
        }

        System.out.println(Math.abs(a - b));
    }

    private static List<int[]> generatePermutations(int n) {
        List<int[]> result = new ArrayList<>();
        int[] elements = new int[n];
        for (int i = 0; i < n; i++) {
            elements[i] = i + 1;
        }
        permute(elements, 0, result);
        return result;
    }

    private static void permute(int[] arr, int k, List<int[]> result) {
        if (k == arr.length) {
            result.add(Arrays.copyOf(arr, arr.length));
            return;
        }

        for (int i = k; i < arr.length; i++) {
            swap(arr, k, i);
            permute(arr, k + 1, result);
            swap(arr, k, i); // backtrack
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}