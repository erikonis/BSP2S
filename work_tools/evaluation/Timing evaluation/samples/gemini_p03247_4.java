import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        long[] X = new long[N];
        long[] Y = new long[N];

        for (int i = 0; i < N; i++) {
            X[i] = sc.nextLong();
            Y[i] = sc.nextLong();
        }

        // Check parity of (X_i + Y_i)
        long parity = (X[0] + Y[0]) % 2;
        if (parity < 0) parity += 2; // Ensure parity is non-negative

        for (int i = 1; i < N; i++) {
            long currentParity = (X[i] + Y[i]) % 2;
            if (currentParity < 0) currentParity += 2;
            if (currentParity != parity) {
                System.out.println(-1);
                return;
            }
        }

        int m;
        long[] d;

        // If parity is 0, we need an extra arm of length 1 to change parity
        if (parity == 0) {
            m = 40; // Max m allowed is 40
            d = new long[m];
            for (int i = 0; i < m - 1; i++) {
                d[i] = 1L << (m - 2 - i); // Powers of 2: 2^38, 2^37, ..., 2^0
            }
            d[m - 1] = 1; // The extra arm
        } else {
            m = 39; // Max m allowed is 40
            d = new long[m];
            for (int i = 0; i < m; i++) {
                d[i] = 1L << (m - 1 - i); // Powers of 2: 2^38, 2^37, ..., 2^0
            }
        }

        System.out.println(m);
        for (int i = 0; i < m; i++) {
            System.out.print(d[i] + (i == m - 1 ? "" : " "));
        }
        System.out.println();

        for (int i = 0; i < N; i++) {
            long currentX = X[i];
            long currentY = Y[i];
            StringBuilder sb = new StringBuilder();

            // If we added an extra arm for parity 0, adjust target coordinates
            if (parity == 0) {
                currentX -= 1; // Effectively move (0,0) to (-1,0) using the last arm 'R'
                sb.append('R'); // The last arm is always 'R'
            }

            // Transform coordinates to u = x+y, v = x-y
            // This allows independent movement along u and v axes using L/R and U/D
            // (x,y) -> (x+y, x-y)
            // (x+d,y) -> (x+y+d, x-y+d)  (R)
            // (x-d,y) -> (x+y-d, x-y-d)  (L)
            // (x,y+d) -> (x+y+d, x-y-d)  (U)
            // (x,y-d) -> (x+y-d, x-y+d)  (D)

            // Let's use the standard mapping:
            // (x,y) -> (u,v) where u = x+y, v = x-y
            // If we move R by d: (x+d, y) -> (u+d, v+d)
            // If we move L by d: (x-d, y) -> (u-d, v-d)
            // If we move U by d: (x, y+d) -> (u+d, v-d)
            // If we move D by d: (x, y-d) -> (u-d, v+d)

            // We want to reach (X_i, Y_i) using powers of 2.
            // The key idea is to use the sum/difference transformation.
            // (x, y) coordinates can be reached by a sequence of moves.
            // Each move is +d or -d in x or y.
            // Consider the sum x+y and difference x-y.
            // When we move (x,y) to (x',y') by length d_k:
            // R: (x+d_k, y) -> (x+d_k+y, x+d_k-y) sum changes by d_k, diff changes by d_k
            // L: (x-d_k, y) -> (x-d_k+y, x-d_k-y) sum changes by -d_k, diff changes by -d_k
            // U: (x, y+d_k) -> (x+y+d_k, x-y-d_k) sum changes by d_k, diff changes by -d_k
            // D: (x, y-d_k) -> (x+y-d_k, x-y+d_k) sum changes by -d_k, diff changes by d_k

            // Notice that the parity of (sum of coordinates) and (difference of coordinates) remains the same
            // for each move (R, L, U, D) if d_k is odd.
            // With d_k as powers of 2, all d_k are even except for 2^0 = 1.
            // This is why the parity of (X_i + Y_i) is crucial.

            // A standard approach for powers of 2 is to simulate the binary representation.
            // We want to reach (currentX, currentY) from (0,0).
            // For each arm d_j, we decide its direction.
            // We iterate from the largest power of 2 down to 1.
            // At each step, we determine the direction that brings us closer to the target.

            // The 'alternative method' commonly refers to this:
            // For each (x, y), calculate u = x+y and v = x-y.
            // Then, for each arm length d_k (from largest to smallest):
            // We want to make a choice such that (target_u - current_u) and (target_v - current_v) are reduced.
            // If we choose R: (u,v) -> (u+d, v+d)
            // If we choose L: (u,v) -> (u-d, v-d)
            // If we choose U: (u,v) -> (u+d, v-d)
            // If we choose D: (u,v) -> (u-d, v+d)

            // Let's use a greedy approach for each power of 2.
            // We want to end up at (currentX, currentY).
            // The coordinates (x,y) can be thought of as a sum of vectors.
            // (x,y) = sum(sign_i * (d_i, 0)) or sum(sign_i * (0, d_i))
            // The sum of all d_i is max_sum_d.
            // Each coordinate can be represented as (sum of some d_i) - (sum of others d_i).
            // (x + y) must be sum of d_i with specific signs.
            // (x - y) must be sum of d_i with specific signs.

            // The key is that (x+y) and (x-y) must have the same parity.
            // (x+y) - (x-y) = 2y, which is always even. So (x+y) and (x-y) must have the same parity.
            // This implies that X and Y must have the same parity, or different parities.
            // If X and Y have the same parity, X+Y is even. If X and Y have different parities, X+Y is odd.
            // So, all (X_j + Y_j) must have the same parity. This is the initial check.

            // For each step k from m-1 down to 0 (or m-2 down to 0 if parity is 0):
            // We are at (x_curr, y_curr) and want to reach (X_i, Y_i).
            // We have arm d[k].
            // We want to choose a direction for d[k] such that it brings us closer.
            // The remaining sum of lengths is S_rem = sum(d_j for j < k).
            // We need |X_i - x_curr| <= S_rem and |Y_i - y_curr| <= S_rem.
            // And (X_i - x_curr) + (Y_i - y_curr) must have the same parity as S_rem.

            // This is a typical "sum of powers of 2" problem.
            // Think about coordinate transformation: (x,y) -> (x+y, x-y).
            // Let (U, V) = (X_i + Y_i, X_i - Y_i).
            // Our current position in (u,v) space starts at (0,0).
            // Move R (d): (u+d, v+d)
            // Move L (d): (u-d, v-d)
            // Move U (d): (u+d, v-d)
            // Move D (d): (u-d, v+d)

            // For each d_k (from largest to smallest):
            // We need to decide if we move (d_k, d_k), (-d_k, -d_k), (d_k, -d_k), or (-d_k, d_k) in (u,v) space.
            // Let (u_target, v_target) be (currentX + currentY, currentX - currentY).
            // We want to reduce the distance to (u_target, v_target).
            // Which direction minimizes the remaining (u,v) difference?

            // This is the "recursive" solution often used:
            // For each d_k in d (from largest to smallest):
            // Consider the four possible next positions (nx, ny):
            // R: (currentX + d_k, currentY)
            // L: (currentX - d_k, currentY)
            // U: (currentX, currentY + d_k)
            // D: (currentX, currentY - d_k)
            // We pick the one that leaves the remaining difference (X_i - nx) + (Y_i - ny) minimal in absolute value
            // and has the correct parity for the remaining sum of lengths.

            // The method described by AtCoder editorials for this problem often works by ensuring
            // that at each step, the absolute value of (target_coord - current_coord) is reduced.
            // For a coordinate `p` and length `len`, we want to go `p - len` or `p + len`.
            // The key is that `p` must be reachable.
            // If `p` is the target, and we have `d_k`, we want `p` to be `current_pos +/- d_k`.
            // So `current_pos = p +/- d_k`.
            // This means `|p - current_pos| = d_k`.

            // Let's use the typical approach for powers of 2.
            // We are trying to construct X and Y as sum/difference of d_i.
            // The `d` array is powers of 2 (2^38, 2^37, ..., 2^0).
            // If (X_i + Y_i) is even, we use (2^38, ..., 2^1, 1) and an extra arm of 1.
            // If (X_i + Y_i) is odd, we use (2^38, ..., 2^0).

            // Let (x_rem, y_rem) be (currentX, currentY) for the loop.
            // We iterate from m-1 down to 0 for the d_i values.
            // For the j-th arm (d[j]), we want to determine its direction.
            // Let's use the actual coordinate transformation.
            // (x, y) -> (x+y, x-y)
            // target (X_i, Y_i) -> (U_target, V_target) = (X_i + Y_i, X_i - Y_i)
            // Current position (x_curr, y_curr) -> (u_curr, v_curr)

            // Let's iterate through the arms `d_k` from largest to smallest.
            // For each `d_k`, we have four choices.
            // We want to make sure that (currentX + currentY) and (currentX - currentY) are
            // properly constructed.
            // The crucial part is that (X_i + Y_i) and (X_i - Y_i) must have the same parity.
            // And each d_k is a power of 2, so its parity is 0 (even) except for 2^0 = 1.

            // The standard binary decomposition trick:
            // For each component (x or y), we want to get to the target.
            // Consider the vector (currentX, currentY).
            // We have a set of lengths d_k.
            // We need to find signs for each d_k such that sum(sign_k * d_k * unit_vector_k) = (X_i, Y_i).
            // The unit vectors are (1,0), (-1,0), (0,1), (0,-1).

            // The trick is to convert to (u,v) coordinates where u = x+y and v = x-y.
            // (X_i, Y_i) -> (U_i, V_i) = (X_i + Y_i, X_i - Y_i)
            // We need to achieve (U_i, V_i) using operations on (u,v):
            // R: (u,v) -> (u+d, v+d)
            // L: (u,v) -> (u-d, v-d)
            // U: (u,v) -> (u+d, v-d)
            // D: (u,v) -> (u-d, v+d)

            // Notice that (u+v)/2 = x and (u-v)/2 = y.
            // So if we find (u,v) we can find (x,y).
            // Also, (u_new + v_new) will always have the same parity as (u_old + v_old) + 2*d (for R/L) or (u_old + v_old) (for U/D).
            // This is not what we want.
            // The sum (u+v) and (u-v) are always even. So u and v must have the same parity.
            // (X+Y) and (X-Y) must have the same parity. This is automatically satisfied if (X+Y) is even/odd.

            // Let's try to achieve (U_i, V_i) greedily using powers of 2.
            // For each arm d_j from largest to smallest:
            // We want to adjust (u_curr, v_curr) towards (U_i, V_i).
            // Consider the parity of U_i and V_i. Since U_i and V_i have the same parity, they are both odd or both even.
            // If they are both odd (initial parity check was odd), then all d_j are powers of 2, including 1.
            // If they are both even (initial parity check was even), then all d_j are powers of 2, and we have an extra '1' arm.
            // This '1' arm is used to adjust the parity.

            // The 'alternative method' is often one that doesn't use the parity trick with an extra arm.
            // Instead, it works for one parity, and if the other parity is needed, it transforms the coordinates.
            // However, the problem statement explicitly allows for different 'm' and 'd_i' for different parities.

            // Let's use the provided solution structure, which is common for this type of problem.
            // The `d` values are powers of 2.
            // For each point (X_i, Y_i):
            // We essentially need to express X_i and Y_i as sums/differences of d_k.
            // Let (x_curr, y_curr) be the current position, starting at (0,0).
            // We iterate from k = m-1 down to 0 (or m-2 down to 0 if parity is 0, for the main powers of 2).
            // At each step, we have `d[k]`. We want to move towards (X_i, Y_i).
            // The remaining distance is (X_i - x_curr, Y_i - y_curr).
            // If we move R, x_curr += d[k]. If L, x_curr -= d[k]. If U, y_curr += d[k]. If D, y_curr -= d[k].
            //
            // A common way to handle this is to always move towards the larger coordinate and use the remaining
            // smaller powers of 2 to fix the exact position.
            //
            // Let's use the transform (x, y) -> (x+y, x-y).
            // (x,y) = ((x+y)+(x-y))/2, ((x+y)-(x-y))/2
            // So if we can reach (U, V) where U=x+y and V=x-y, we can reach (x,y).
            // All d_k are positive.
            //
            // For each (X, Y) target:
            // current_x = 0, current_y = 0
            // For each d_j:
            //   If abs(X - (current_x + d_j)) <= sum_remaining_d and abs(Y - current_y) <= sum_remaining_d:
            //     Try R
            //   Else if abs(X - (current_x - d_j)) <= sum_remaining_d and abs(Y - current_y) <= sum_remaining_d:
            //     Try L
            //   ... and so on.
            // This is too complex with the sum_remaining_d.

            // The simpler "alternative" for powers of 2:
            // For each target (X_i, Y_i):
            // Initial position (px, py) = (0,0).
            // Iterate k from m-1 down to 0 (or m-2 down to 0 if parity is 0 for the main powers of 2).
            // Let current_d = d[k].
            //
            // The key is to realize that after transforming to (u,v) = (x+y, x-y),
            // all moves (R, L, U, D) change both u and v by +/- d_k.
            // Specifically:
            // R: u += d_k, v += d_k
            // L: u -= d_k, v -= d_k
            // U: u += d_k, v -= d_k
            // D: u -= d_k, v += d_k
            //
            // Let (u_target, v_target) = (currentX + currentY, currentX - currentY).
            // Let (u_curr, v_curr) = (0,0).
            // For each d_j (from largest to smallest, excluding the last '1' if parity is 0):
            // We want to move (u_curr, v_curr) towards (u_target, v_target).
            // We decide the direction based on the signs of (u_target - u_curr) and (v_target - v_curr).
            // This is the standard method for powers of 2.
            //
            // If (u_target - u_curr) > 0 and (v_target - v_curr) > 0: Choose R (u+d, v+d)
            // This moves u and v in positive direction.
            // If (u_target - u_curr) < 0 and (v_target - v_curr) < 0: Choose L (u-d, v-d)
            // This moves u and v in negative direction.
            // If (u_target - u_curr) > 0 and (v_target - v_curr) < 0: Choose U (u+d, v-d)
            // This moves u positive, v negative.
            // If (u_target - u_curr) < 0 and (v_target - v_curr) > 0: Choose D (u-d, v+d)
            // This moves u negative, v positive.
            //
            // After each move, update (u_curr, v_curr).
            // At the end, (u_curr, v_curr) should be (u_target, v_target).
            // This only works if u_target and v_target are representable by sum/diff of d_j.
            // Since d_j are powers of 2, this is effectively binary representation.
            //
            // Example: d = {4, 2, 1}. Target (u,v) = (5, 3).
            // Initial (u_curr, v_curr) = (0,0).
            // d_0 = 4:
            //   (u_target - u_curr) = 5 > 0, (v_target - v_curr) = 3 > 0.
            //   Choose R. (u_curr, v_curr) = (0+4, 0+4) = (4,4). sb.append('R');
            // d_1 = 2:
            //   (u_target - u_curr) = 5-4 = 1 > 0, (v_target - v_curr) = 3-4 = -1 < 0.
            //   Choose U. (u_curr, v_curr) = (4+2, 4-2) = (6,2). sb.append('U');
            // d_2 = 1:
            //   (u_target - u_curr) = 5-6 = -1 < 0, (v_target - v_curr) = 3-2 = 1 > 0.
            //   Choose D. (u_curr, v_curr) = (6-1, 2+1) = (5,3). sb.append('D');
            // Final (u_curr, v_curr) = (5,3). This matches.
            // The string is RUD.

            // This approach works for all powers of 2.
            // The problem is the parity.
            // If (X_i + Y_i) is even, then (X_i + Y_i) and (X_i - Y_i) are both even.
            // If we only use arms of length 2^k (k > 0), then we can only reach even (u,v) coordinates.
            // If (X_i + Y_i) is odd, then (X_i + Y_i) and (X_i - Y_i) are both odd.
            // If we only use arms of length 2^k (k >= 0), then we can reach odd (u,v) coordinates.
            // This is why the parity check is crucial.

            long u_target = currentX + currentY;
            long v_target = currentX - currentY;

            long u_curr = 0;
            long v_curr = 0;

            for (int j = 0; j < m; j++) {
                if (parity == 0 && j == 0) { // This is the extra arm if parity is 0
                    // The first arm d[0] is 2^38, then 2^37, ..., 2^0, then the extra 1.
                    // If m=40, d[0]...d[38] are 2^38...2^0, d[39] is 1.
                    // The main loop for powers of 2 (2^38 down to 2^0) is for d[0] to d[m-2].
                    // The last arm d[m-1] is the extra 1.
                    // If parity is 0, we already did `currentX -= 1; sb.append('R');`
                    // This means (u_target, v_target) is effectively (original_X - 1 + original_Y, original_X - 1 - original_Y).
                    // The iteration for d_j should be from the largest power of 2 down to 1.
                    // The extra arm (if any) is then handled.

                    // Let's restart the loop with correct indices.
                    // For m=40, d = {2^38, ..., 2^0, 1}
                    // For m=39, d = {2^38, ..., 2^0}

                    // The example solution uses d_1, d_2, ..., d_m.
                    // So d[0] is d_1, d[m-1] is d_m.
                    // If parity is 0, m=40. d[0] = 2^38, ..., d[38] = 2^0, d[39] = 1.
                    // We must use d[39] = 1. We chose 'R' for it, so our target is effectively (X-1, Y).
                    // This makes (X-1+Y) and (X-1-Y) odd.
                    // So we work with (u_target_adj, v_target_adj) where (u_target_adj, v_target_adj) = (X-1+Y, X-1-Y).
                    // And we use d[0]...d[38] (which are 2^38...2^0).
                    // This means the loop for (u,v) transformation should be for j from 0 to m-2 (inclusive).
                    // The last character is for d[m-1].
                }

                // If parity is 0, we have an extra arm `d[m-1] = 1`.
                // We've already accounted for it by subtracting 1 from `currentX` and adding 'R' to `sb`.
                // Now, `u_target` and `v_target` (the modified ones) are both odd.
                // And we use `d[0]` through `d[m-2]`, which are `2^38` down to `2^0`.
                // This means `m_prime = m-1` arms.
                //
                // If parity is 1, `m=39`. `d[0]` through `d[38]` are `2^38` down to `2^0`.
                // `u_target` and `v_target` are both odd.
                // This means `m_prime = m` arms.

                long current_d;
                if (parity == 0) { // m = 40, d has 40 elements. d[39] is the extra 1.
                    if (j == m - 1) { // The last arm, which is the extra `1`.
                        // This case should be skipped here, as it's handled by initial adjustment.
                        // Or rather, the loop should go up to m-2 for the main arms if parity 0.
                        // And the last arm is handled.
                        // Re-evaluate loop boundaries and d array usage.
                    }
                    current_d = d[j]; // d[0]...d[38] are 2^38...2^0. d[39] is 1.
                                       // If parity is 0, we're targeting odd numbers (after adjusting by 1).
                                       // We iterate through d[0]...d[m-2] (2^38 to 2^0).
                                       // This means the last arm is not used in the u/v calculation.
                                       // The last arm is d[m-1].
                } else { // parity == 1, m = 39. d has 39 elements. d[0]...d[38] are 2^38...2^0.
                    current_d = d[j];
                }

                // Decide the direction based on (u_target - u_curr) and (v_target - v_curr)
                // This loop goes from largest d_j to smallest.
                // The directions are determined such that (u_target - u_curr) and (v_target - v_curr)
                // are exactly 0 at the end.
                // We need to pick the direction that reduces the absolute value of the differences.
                // For example, if u_target - u_curr = 5 and d_j = 4, then u_curr should become 4 or -4.
                // If we make u_curr = 4, remaining diff is 1.
                // If we make u_curr = -4, remaining diff is 9.
                // So pick the closest one.
                // This is effectively `(target - current) / 2` logic for binary.

                // The standard way:
                // Current (x_pos, y_pos). Target (X_i, Y_i).
                // Use the largest d_k first.
                // (x_pos, y_pos) starts at (0,0).
                // For each d_k (from largest to smallest):
                //   If (X_i - x_pos) > (Y_i - y_pos):
                //     If (X_i - x_pos) > -(Y_i - y_pos):
                //       Move R: x_pos += d_k; sb.append('R');
                //     Else:
                //       Move U: y_pos += d_k; sb.append('U');
                //   Else:
                //     If (X_i - x_pos) > -(Y_i - y_pos):
                //       Move D: y_pos -= d_k; sb.append('D');
                //     Else:
                //       Move L: x_pos -= d_k; sb.append('L');

                // This logic is for the original (x,y) coordinates.
                // Let's use it for the adjusted target if parity is 0.

                long dx = currentX - u_curr; // currentX is the target for x. u_curr is current x.
                long dy = currentY - v_curr; // currentY is the target for y. v_curr is current y.

                // This is the core logic for constructing the path using powers of 2.
                // At each step, we determine which quadrant the target is relative to the current position.
                // And then, which of the four moves (R, L, U, D) brings us closer.
                // The remaining sum of lengths (S_rem) is sum(d_k for k < current_index).
                // We need to ensure that |dx| <= S_rem and |dy| <= S_rem after the current move.
                // And also (dx + dy) % 2 == S_rem % 2.

                // The standard way to achieve (X,Y) from (0,0) using powers of 2:
                // For each d_k from largest to smallest:
                // Calculate the remaining distance to target (X_i - current_x, Y_i - current_y).
                // Then, determine which quadrant this remaining distance vector points to.
                // And based on that, choose the direction that "covers" the largest part of the remaining distance.
                // This is often done by "folding" the coordinates.
                // Example: If target X is positive, and current_x is 0, and d_k is large.
                // If X > 0, we generally want to move Right. If X < 0, generally Left.
                // Same for Y with Up/Down.

                // The (u,v) transformation is cleaner for this.
                // u_curr, v_curr starts at (0,0).
                // For each d_k (from largest to smallest):
                // Calculate (diff_u, diff_v) = (u_target - u_curr, v_target - v_curr).
                // If diff_u and diff_v are both positive: Move R. u_curr += d_k, v_curr += d_k.
                // If diff_u and diff_v are both negative: Move L. u_curr -= d_k, v_curr -= d_k.
                // If diff_u positive, diff_v negative: Move U. u_curr += d_k, v_curr -= d_k.
                // If diff_u negative, diff_v positive: Move D. u_curr -= d_k, v_curr += d_k.

                // This greedy choice works because all d_k are powers of 2, and their sum forms
                // a "binary representation" of the target coordinate.
                // It's like finding the binary representation for u and v simultaneously.

                // Let's re-implement for (u,v) coordinates.
                long temp_u = u_target - u_curr;
                long temp_v = v_target - v_curr;

                long current_d_val;
                if (parity == 0 && j == m - 1) { // This is the last arm (length 1) for even parity.
                    // Its direction was already determined as 'R' to adjust parity.
                    // No further (u,v) calculation needed for this arm.
                    // This arm 'R' means (u_curr, v_curr) effectively moved by (1,1).
                    // But we already adjusted the target (currentX) by -1.
                    // So effectively, we're solving for (X-1, Y), and that `R` is for `d[m-1]`.
                    // The main loop should only be for `d[0]` to `d[m-2]`.

                    // This means the loop should be `for (int j = 0; j < (parity == 0 ? m - 1 : m); j++)`.
                    // And the `sb.append('R')` for the extra arm must be outside this loop.
                    // Let's restructure.
                    break; // Exit the loop if it's the extra arm
                }
                current_d_val = d[j];

                if (temp_u > 0 && temp_v > 0) {
                    sb.append('R');
                    u_curr += current_d_val;
                    v_curr += current_d_val;
                } else if (temp_u < 0 && temp_v < 0) {
                    sb.append('L');
                    u_curr -= current_d_val;
                    v_curr -= current_d_val;
                } else if (temp_u > 0 && temp_v < 0) {
                    sb.append('U');
                    u_curr += current_d_val;
                    v_curr -= current_d_val;
                } else { // temp_u < 0 && temp_v > 0
                    sb.append('D');
                    u_curr -= current_d_val;
                    v_curr += current_d_val;
                }
            }

            // If parity was 0, append the 'R' for the last arm.
            if (parity == 0) {
                sb.append('R');
            }

            System.out.println(sb.toString());
        }
    }
}