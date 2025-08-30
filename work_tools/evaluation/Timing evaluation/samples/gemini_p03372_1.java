import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        long C = sc.nextLong();
        long[] x = new long[N];
        long[] v = new long[N];
        for (int i = 0; i < N; i++) {
            x[i] = sc.nextLong();
            v[i] = sc.nextLong();
        }

        long[] sumVRight = new long[N + 1];
        long[] maxRight = new long[N + 1];
        for (int i = 0; i < N; i++) {
            sumVRight[i + 1] = sumVRight[i] + v[i];
            maxRight[i + 1] = Math.max(maxRight[i], sumVRight[i + 1] - x[i]);
        }

        long[] sumVLeft = new long[N + 1];
        long[] maxLeft = new long[N + 1];
        for (int i = 0; i < N; i++) {
            sumVLeft[i + 1] = sumVLeft[i] + v[N - 1 - i];
            maxLeft[i + 1] = Math.max(maxLeft[i], sumVLeft[i + 1] - (C - x[N - 1 - i]));
        }

        long ans = 0;

        // Case 1: Go clockwise, then turn around and go counter-clockwise
        for (int i = 0; i < N; i++) {
            // Eat sushi up to index i (0 to i) clockwise
            long currentV = sumVRight[i + 1];
            long currentCost = x[i];
            // Then go counter-clockwise from x[i]
            // We need to find the best sushi to eat counter-clockwise from x[i]
            // This means eating sushi from N-1 down to 0, but only those that are "before" x[i]
            // from the perspective of going counter-clockwise.
            // This is equivalent to finding the best sum for (C - x_j) - (C - x_i) cost
            // or x_i - x_j if x_j < x_i.
            // It's simpler to think of it as going from 0 to i clockwise, and then from C-1 to 0 counter-clockwise.
            // The remaining path would be from C-x_j to C-x_i
            // So, we are looking for the optimal path for sumVLeft[k] - (C - x[N-k])
            // but the travel cost from x[i] back to x[N-1-k] is x[i] + (C - x[N-1-k])
            // and the cost for the sushi itself is x[i].
            // To simplify, consider the point where we turn around.
            // If we eat sushi up to index i clockwise (cost x[i]), then turn around
            // and eat sushi from index N-1 down to index j (cost C - x[j]).
            // The total path would be x[i] + (C - x[j]).
            // The total value would be sumVRight[i+1] + (sumVLeft[N-j] - sumVLeft[N-(i+1)])
            // This is complex. Let's use the precomputed maxRight and maxLeft.

            // Option A: Only go clockwise
            ans = Math.max(ans, sumVRight[i + 1] - x[i]);

            // Option B: Go clockwise to x[i], then turn back and go counter-clockwise.
            // We are at x[i], and we want to go counter-clockwise.
            // Any sushi to the "left" of 0 (in clockwise terms) is effectively C - x_j.
            // The turn around cost is x[i]
            // The additional cost for counter-clockwise travel from 0 is (C - x_j)
            // Total cost = x[i] + (C - x_j)
            // Total value = sumVRight[i+1] + sum of values for sushi eaten counter-clockwise.
            // The maximum value for going counter-clockwise from 0 to some point j is maxLeft[k] (where k is how many sushi we ate).
            // The cost for the clockwise path is x[i].
            // The cost for the counter-clockwise path is (C - x[N-k]).
            // The total cost is x[i] + (C - x[N-k]). We visited x[i] once.
            // The sushi up to index i (clockwise) have been consumed.
            // The sushi from index N-1 down to index N-1-k (counter-clockwise) have been consumed.
            // We must make sure we don't double count sushi.
            // The loop for i covers the rightmost sushi eaten clockwise.
            // The loop for j covers the leftmost sushi eaten counter-clockwise.

            // The correct way to think about this is:
            // 1. Go clockwise to x[i], eating all sushi up to x[i].
            //    Net value = sumVRight[i+1] - x[i].
            // 2. From x[i], go back to 0 (cost x[i]), then go counter-clockwise to some x[j] (cost C-x[j]).
            //    Total cost for this path: x[i] + (x[i] + (C - x[j])) = 2*x[i] + (C - x[j])
            //    Total value: sumVRight[i+1] + sum of values from x[N-1] down to x[j] (exclusive of sushi already eaten).
            //    This is getting complicated due to overlapping sushi.

            // Let's analyze the states:
            // Iterate `i` from 0 to N-1. This `i` represents the rightmost sushi eaten (clockwise).
            // `sumVRight[i+1]` is the total value of sushi from 0 to `i`.
            // `x[i]` is the cost to reach `x[i]`.
            // We can either stop at `x[i]` or go further.
            // If we stop at `x[i]`, the net is `sumVRight[i+1] - x[i]`. This is covered by `maxRight`.

            // Consider the case where we go clockwise to `x[i]`, then turn back and go counter-clockwise.
            // We are at `x[i]`. We have spent `x[i]` energy.
            // We want to eat sushi from the counter-clockwise side.
            // From `x[i]`, we go back to 0 (cost `x[i]`).
            // Then from 0, we go counter-clockwise to some `x[j]` (cost `C - x[j]`).
            // Total cost for this path: `2 * x[i] + (C - x[j])`.
            // The sushi eaten are from `0` to `i` (clockwise), and from `N-1` down to `j` (counter-clockwise).
            // We need to ensure `x[j]` is not among `x[0]`...`x[i]`.
            // This means `j` must be greater than `i`.
            // So for each `i` (rightmost clockwise sushi), we try to find the best `j` (leftmost counter-clockwise sushi).
            // The `j` here is the index in the original array.
            // The sushi are `x[N-1], x[N-2], ..., x[j]`.
            // The values are `v[N-1], v[N-2], ..., v[j]`.
            // The distances are `C-x[N-1], C-x[N-2], ..., C-x[j]`.
            // We need `maxLeft[k]` where `k` is the number of sushi eaten from the left.
            // `maxLeft[k]` is `sumVLeft[k] - (C - x[N-k])`.
            // This `maxLeft[k]` is the best value if we only go counter-clockwise and eat `k` sushi.
            // If we go clockwise to `x[i]` (cost `x[i]`, value `sumVRight[i+1]`), then turn around
            // and go counter-clockwise to eat `k` sushi from the left
            // (these sushi are `x[N-1], ..., x[N-k]`).
            // The cost for this counter-clockwise trip is `C - x[N-k]`.
            // The total cost is `x[i] + x[i] + (C - x[N-k])`. (Go to x[i], return to 0, go to x[N-k])
            // The total value is `sumVRight[i+1] + sumVLeft[k]`.
            // Net = `sumVRight[i+1] + sumVLeft[k] - (2*x[i] + (C - x[N-k]))`.
            // We need to be careful with indices. `k` goes from `0` to `N-1-i`.
            // The sushi from `N-1` down to `i+1` are available for counter-clockwise eating.
            // The number of such sushi is `(N-1) - (i+1) + 1 = N-1-i`.
            // So `k` for `maxLeft` goes up to `N-1-i`.
            // `maxLeft[k]` corresponds to eating sushi `x[N-1], ..., x[N-k]`.
            // `sumVLeft[k]` is `v[N-1] + ... + v[N-k]`.
            // `C - x[N-k]` is the distance for the k-th sushi from the left.

            // Let's iterate `i` for the rightmost sushi eaten clockwise.
            // `current_clockwise_val = sumVRight[i+1]`.
            // `current_clockwise_cost = x[i]`.
            // `net_if_only_clockwise = current_clockwise_val - current_clockwise_cost`.
            // `ans = Math.max(ans, net_if_only_clockwise)`. (This is already covered by `maxRight`)

            // Now, consider going clockwise to `x[i]`, then turning around.
            // We are at `x[i]`. We have accumulated `current_clockwise_val`.
            // We travel back to 0 (cost `x[i]`).
            // Now we are at 0, and have `current_clockwise_val - 2*x[i]` net.
            // From 0, we can go counter-clockwise and eat some sushi.
            // The best we can get from going counter-clockwise to eat `k` sushi is `maxLeft[k]`.
            // This `maxLeft[k]` already includes the value of sushi and subtracts the distance from 0.
            // So, `ans = Math.max(ans, current_clockwise_val - 2*x[i] + maxLeft[k])`.
            // Here `k` refers to the number of sushi eaten from the left.
            // The sushi eaten clockwise are `0 ... i`.
            // The sushi eaten counter-clockwise are `N-1 ... N-k`.
            // We need `N-k > i` for these sets to be disjoint.
            // So `k < N-i`.
            // Iterating `i` from 0 to N-1:
            //   `val_R = sumVRight[i+1]`
            //   `cost_R = x[i]`
            //   `ans = max(ans, val_R - cost_R)` (This is handled by `maxRight`)
            //   `ans = max(ans, val_R - 2*cost_R + maxLeft[N-(i+1)])` where `N-(i+1)` is the number of sushi available for counter-clockwise.
            //   No, `maxLeft[k]` means we eat `k` sushi from the left. These are `x[N-1], ..., x[N-k]`.
            //   We need to ensure `N-k > i`. So `k < N-i`.
            //   So we can take `k` from `0` to `N-1-i`.
            //   `maxLeft[j]` for `j` from 0 to `N-1-i`.
            //   `ans = Math.max(ans, sumVRight[i + 1] - 2 * x[i] + maxLeft[j])` for `j` from 0 to `N-1-i`.
            //   This is equivalent to `sumVRight[i+1] - 2*x[i] + max_val_from_left_that_does_not_overlap`.
            //   The `maxLeft` array is already designed to give the maximum value for eating `k` sushi from the left.
        }

        // The maximum value can be obtained by either:
        // 1. Go clockwise, then come back.
        // 2. Go counter-clockwise, then come back.
        // 3. Go clockwise, and stop.
        // 4. Go counter-clockwise, and stop.

        // Max value if only going clockwise (and stopping)
        // This is `maxRight[N]`.
        // Max value if only going counter-clockwise (and stopping)
        // This is `maxLeft[N]`.
        ans = Math.max(maxRight[N], maxLeft[N]);

        // Case 1: Go clockwise up to `x[i]`, then turn back and pick up sushi counter-clockwise.
        // Iterate `i` from 0 to N-1 (rightmost sushi eaten clockwise).
        // Total value from clockwise path: `sumVRight[i+1]`.
        // Cost to reach `x[i]`: `x[i]`.
        // To turn back and pick up sushi from left, we travel `x[i]` back to origin, then `C-x[j]` to `x[j]` (leftmost sushi eaten counter-clockwise).
        // The total cost for the *second* leg of the journey (from origin to `x[j]` and back to origin) is `2 * (C - x[j])`.
        // No, this is not right.
        // The point is: We go to `x[i]`, collect all sushi on the way. Net `sumVRight[i+1] - x[i]`.
        // Then we decide to go further counter-clockwise.
        // From `x[i]`, we go back to 0 (cost `x[i]`).
        // Then from 0, we go to `x[N-1-j]` (cost `C - x[N-1-j]`) eating `j+1` sushi from the left.
        // The maximum net value from `j+1` sushi from the left is `maxLeft[j+1]`.
        // The total cost of travel for this strategy is `x[i] + x[i] + (C - x[N-1-j])`.
        // The total value is `sumVRight[i+1] + sumVLeft[j+1]`.
        // Net = `sumVRight[i+1] + sumVLeft[j+1] - (2*x[i] + (C - x[N-1-j]))`.
        // This is equivalent to `(sumVRight[i+1] - 2*x[i]) + (sumVLeft[j+1] - (C - x[N-1-j]))`.
        // So, `ans = max(ans, (sumVRight[i+1] - 2*x[i]) + maxLeft[j])` where `j` is the number of sushi eaten from left.
        // The index `j` for `maxLeft` means we eat `j` sushi from the left, i.e., `x[N-1], ..., x[N-j]`.
        // These sushi must not overlap with `x[0], ..., x[i]`. So `N-j` must be greater than `i`.
        // `j < N-i`.
        // So `j` can go from `0` to `N-1-i`.
        for (int i = 0; i < N; i++) {
            // Go clockwise to x[i], eat sushi 0..i
            long currentV_R = sumVRight[i + 1];
            long currentCost_R = x[i];

            // Option 1: Stop at x[i]
            ans = Math.max(ans, currentV_R - currentCost_R);

            // Option 2: Go clockwise to x[i], then turn back to 0, then go counter-clockwise
            // The remaining sushi are from index i+1 to N-1.
            // The number of remaining sushi is N - (i+1).
            int numSushiLeft = N - (i + 1);
            if (numSushiLeft > 0) {
                // We can eat up to `numSushiLeft` sushi from the left (from N-1 down to i+1).
                // The maxLeft array stores the best value for eating `k` sushi from the left (from N-1 down).
                // So maxLeft[numSushiLeft] gives the best value for eating sushi N-1 down to i+1.
                // It's `sumVLeft[numSushiLeft] - (C - x[i+1])` if we eat exactly N-(i+1) sushi.
                // We need the maximum of `sumVLeft[k] - (C - x[N-k])` for `k` up to `numSushiLeft`.
                // This is `maxLeft[numSushiLeft]`.
                ans = Math.max(ans, currentV_R - 2 * currentCost_R + maxLeft[numSushiLeft]);
            }
        }

        // Case 2: Go counter-clockwise up to `x[i]` (from left), then turn back and pick up sushi clockwise.
        // Iterate `i` from 0 to N-1 (leftmost sushi eaten counter-clockwise).
        // This means we eat sushi from N-1 down to N-1-i.
        // Total value from counter-clockwise path: `sumVLeft[i+1]`.
        // Cost to reach `x[N-1-i]` (from left): `C - x[N-1-i]`.
        for (int i = 0; i < N; i++) {
            // Go counter-clockwise to x[N-1-i], eat sushi N-1..N-1-i
            long currentV_L = sumVLeft[i + 1];
            long currentCost_L = C - x[N - 1 - i];

            // Option 1: Stop at x[N-1-i]
            ans = Math.max(ans, currentV_L - currentCost_L);

            // Option 2: Go counter-clockwise to x[N-1-i], then turn back to 0, then go clockwise
            // The remaining sushi are from index 0 to N-2-i.
            // The number of remaining sushi is N-1-i.
            int numSushiRight = N - 1 - i;
            if (numSushiRight > 0) {
                // We can eat up to `numSushiRight` sushi from the right (from 0 up to N-2-i).
                // The maxRight array stores the best value for eating `k` sushi from the right (from 0 up).
                // So maxRight[numSushiRight] gives the best value for eating sushi 0 up to N-2-i.
                ans = Math.max(ans, currentV_L - 2 * currentCost_L + maxRight[numSushiRight]);
            }
        }

        System.out.println(ans);
        sc.close();
    }
}