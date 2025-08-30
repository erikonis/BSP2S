import java.util.*;

public class Main {

    static int N;
    static int M;
    static int[] initialBits;
    static int[] runLengthCode;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        M = sc.nextInt();

        initialBits = new int[N];
        for (int i = 0; i < N; i++) {
            initialBits[i] = sc.nextInt();
        }

        runLengthCode = new int[M];
        for (int i = 0; i < M; i++) {
            runLengthCode[i] = sc.nextInt();
        }

        int minSwaps = Integer.MAX_VALUE;

        // Try target string starting with 0
        int[] target0 = buildTargetString(0);
        if (target0 != null) {
            minSwaps = Math.min(minSwaps, calculateSwaps(target0));
        }

        // Try target string starting with 1
        int[] target1 = buildTargetString(1);
        if (target1 != null) {
            minSwaps = Math.min(minSwaps, calculateSwaps(target1));
        }

        System.out.println(minSwaps);

        sc.close();
    }

    private static int[] buildTargetString(int startBit) {
        int[] target = new int[N];
        int currentBit = startBit;
        int index = 0;
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < runLengthCode[i]; j++) {
                if (index >= N) {
                    // Should not happen given constraints, but for safety
                    return null;
                }
                target[index++] = currentBit;
            }
            currentBit = 1 - currentBit; // Alternate the bit
        }
        return target;
    }

    private static int calculateSwaps(int[] target) {
        // Count occurrences of 0s and 1s in initial and target strings
        int[] initialCounts = new int[2];
        int[] targetCounts = new int[2];
        for (int bit : initialBits) {
            initialCounts[bit]++;
        }
        for (int bit : target) {
            targetCounts[bit]++;
        }

        // If counts don't match, this target is not reachable from initialBits
        // Problem statement guarantees it's reachable, but good to check conceptually
        if (initialCounts[0] != targetCounts[0] || initialCounts[1] != targetCounts[1]) {
            return Integer.MAX_VALUE; // Should not happen
        }

        // Use a modified bubble sort idea: count inversions for elements that need to move
        // This is equivalent to finding the minimum swaps to transform one permutation
        // into another when elements are distinct. Here, elements are not distinct,
        // but we are effectively moving specific 0s and 1s to specific positions.
        // It's the minimum swaps to transform `initialBits` into `target`
        // if we consider positions of 0s and 1s.

        // Create lists of indices for 0s and 1s in the initial string
        List<Integer> initialIndices0 = new ArrayList<>();
        List<Integer> initialIndices1 = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            if (initialBits[i] == 0) {
                initialIndices0.add(i);
            } else {
                initialIndices1.add(i);
            }
        }

        // Create lists of indices for 0s and 1s in the target string
        List<Integer> targetIndices0 = new ArrayList<>();
        List<Integer> targetIndices1 = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            if (target[i] == 0) {
                targetIndices0.add(i);
            } else {
                targetIndices1.add(i);
            }
        }

        int swaps = 0;
        // For each 0 in the target string, find the "closest" 0 from the initial string
        // that hasn't been "moved" yet. This is effectively calculating inversions.
        // This is a standard way to calculate minimum swaps for permutation
        // when elements are not distinct (e.g., sorting an array with duplicates).

        // For 0s
        for (int i = 0; i < targetIndices0.size(); i++) {
            int targetPos = targetIndices0.get(i);
            int initialPos = initialIndices0.get(i);
            swaps += Math.abs(targetPos - initialPos);
        }

        // For 1s
        for (int i = 0; i < targetIndices1.size(); i++) {
            int targetPos = targetIndices1.get(i);
            int initialPos = initialIndices1.get(i);
            swaps += Math.abs(targetPos - initialPos);
        }
        
        // The above simple sum of differences is incorrect for adjacent swaps.
        // It's equivalent to Manhattan distance in a 1D array.
        // For adjacent swaps, it's equivalent to the number of inversions.
        // A common way to calculate minimum adjacent swaps to get from one permutation
        // to another when elements are distinct is by counting inversions.
        // When elements are not distinct, you can map the identical elements
        // to distinct ones based on their original positions.

        // Correct approach: Count inversions
        // If we want to move `initialBits` to `target`, we need to find the
        // permutation `P` such that `initialBits[P[i]] == target[i]`.
        // The minimum number of adjacent swaps to transform an array `A` to `B`
        // (where `B` is a permutation of `A`) is the number of inversions
        // required to sort `A` into `B`.

        // Let's create a mapping from initial original positions to target positions
        // For example, if initial is [1,0,0,1,0,1] and target is [1,0,0,0,1,1]
        // Initial 0s are at indices: 1, 2, 4
        // Target 0s are at indices: 1, 2, 3
        // So initial 0 at index 1 goes to target index 1
        // initial 0 at index 2 goes to target index 2
        // initial 0 at index 4 goes to target index 3
        //
        // Initial 1s are at indices: 0, 3, 5
        // Target 1s are at indices: 0, 4, 5
        // So initial 1 at index 0 goes to target index 0
        // initial 1 at index 3 goes to target index 4
        // initial 1 at index 5 goes to target index 5

        // Create an array `finalPositions` where `finalPositions[i]` stores
        // the target index for the element that was initially at `initialBits[i]`.
        int[] finalPositions = new int[N];
        int current0 = 0;
        int current1 = 0;

        for (int i = 0; i < N; i++) {
            if (initialBits[i] == 0) {
                finalPositions[i] = targetIndices0.get(current0++);
            } else {
                finalPositions[i] = targetIndices1.get(current1++);
            }
        }

        // Now, we need to find the minimum adjacent swaps to sort `finalPositions`
        // into `0, 1, 2, ..., N-1`. This is precisely the number of inversions
        // in the `finalPositions` array.

        return countInversions(finalPositions, 0, N - 1);
    }

    // Merge Sort based inversion counting
    private static int countInversions(int[] arr, int left, int right) {
        if (left >= right) {
            return 0;
        }

        int mid = left + (right - left) / 2;
        int inversions = countInversions(arr, left, mid) + countInversions(arr, mid + 1, right);
        inversions += mergeAndCount(arr, left, mid, right);
        return inversions;
    }

    private static int mergeAndCount(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left;
        int j = mid + 1;
        int k = 0;
        int inversions = 0;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
                // If arr[i] > arr[j], then all remaining elements in the left subarray
                // (from arr[i] to arr[mid]) are also greater than arr[j].
                inversions += (mid - i + 1);
            }
        }

        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        while (j <= right) {
            temp[k++] = arr[j++];
        }

        for (i = left; i <= right; i++) {
            arr[i] = temp[i - left];
        }

        return inversions;
    }
}