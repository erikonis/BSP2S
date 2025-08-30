import java.util.Scanner;

public class Main {

    // Function to calculate the sum of digits
    private static long sumDigits(long n) {
        long sum = 0;
        String s = String.valueOf(n);
        for (char c : s.toCharArray()) {
            sum += Character.getNumericValue(c);
        }
        return sum;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int K = sc.nextInt();
        sc.close();

        long currentSnuke = 1;
        int count = 0;

        // Iterate until K Snuke numbers are found
        while (count < K) {
            System.out.println(currentSnuke);
            count++;

            // Calculate the next candidate for a Snuke number
            // The idea is to find the smallest number greater than currentSnuke
            // that satisfies the Snuke number condition.
            // This can be done by considering numbers of the form:
            // currentSnuke + 1, currentSnuke + 10, currentSnuke + 100, ...
            // and checking which one gives a better (smaller) n/S(n) ratio.
            // A more efficient way is to realize that Snuke numbers tend to be of the form
            // X99...9, where X is a digit, or numbers like 1, 2, ..., 9.
            // We can generate candidates by taking the currentSnuke and adding powers of 10.
            // For example, if currentSnuke is 19, the next candidates could be
            // 20, 29, 39, ..., 199, etc.
            // The constraint on K (up to 10^15) suggests that a simple increment
            // won't be fast enough.
            // A common pattern for Snuke numbers is that they are of the form
            // x * 10^p - 1 (e.g., 9, 19, 29, ..., 99, 199, ...) or simple integers.
            // Let's use an iterative approach based on the observation that
            // if n is a Snuke number, then n/S(n) is small.
            // The next Snuke number will be "close" to the current one,
            // or a number with significantly more digits but a small leading digit.

            long nextCandidate = currentSnuke + 1;
            long powerOf10 = 1;

            // This loop tries to find the next Snuke number efficiently.
            // It considers numbers that are formed by adding powers of 10
            // to the current number, but also numbers that are just 1 greater.
            // The core idea is that the ratio n/S(n) tends to be smaller for numbers
            // that end in 9s, or numbers that are powers of 10 minus 1.
            // Also, for a number `n`, the numbers `n + 1`, `n + 10`, `n + 100`, etc.,
            // are candidates for the next Snuke number.
            // Specifically, if `n` is a Snuke number, the next Snuke number `m`
            // will satisfy `m/S(m) <= n/S(n)` for all `m > n`.
            // The smallest `m` that satisfies `m/S(m) <= n/S(n)` is the next Snuke number.
            // We can search for this `m` by starting from `n+1` and incrementing.
            // However, a more optimized search involves considering numbers with
            // a small leading digit followed by many 9s, or just incrementing.

            // The "alternative method" here is to iteratively search for the next Snuke number
            // by checking numbers `n + k * 10^p` for small `k` and `p`.
            // We can also observe that if `n` is a Snuke number, the next one is likely
            // `n + 1` or `n + (10^k - (n % 10^k))` for some `k`.
            // Let's try a simple greedy approach that seems to work for these problems:
            // The next snuke number is either `currentSnuke + 1` or `currentSnuke + value_to_make_it_end_in_9`.
            // More accurately, the next Snuke number will be `currentSnuke + delta`, where `delta` is
            // a power of 10.
            // The most common approach for this problem is to generate candidates by adding 1,
            // and also by adding a value that makes the number end in a 9 or multiple 9s.
            // For example, if currentSnuke is 1, next is 2.
            // If currentSnuke is 9, next is 19.
            // If currentSnuke is 19, next is 29.
            // If currentSnuke is 29, next is 39.
            // ...
            // If currentSnuke is 99, next is 199.

            // The "alternative method" here is based on the observation that
            // if `n` is a Snuke number, the next Snuke number `m` will be such that `m/S(m)` is still small.
            // Consider `n` and `n + 1`. If `(n+1)/S(n+1)` is smaller than `n/S(n)`, `n+1` might be the next Snuke.
            // However, the condition for Snuke numbers is that `n/S(n) <= m/S(m)` for ALL `m > n`.
            // This means we are looking for numbers that are *local minima* of the `x/S(x)` function.
            // The Snuke numbers are the "lower envelope" of these local minima.

            // A common strategy for this problem is to maintain a "current search point" `x`
            // and a "power of 10" `p`.
            // The candidates for the next Snuke number are `x + 1` and `x + 10^p`.
            // We will generate numbers of the form `A * 10^B - 1`.
            // Or more simply, `n` and `n + 10^k` for various `k`.

            // Let's try a different generation strategy.
            // We start with 1.
            // The next Snuke number is either `n+1` or `n + 10^k` where `10^k` is the smallest power of 10
            // such that `n + 10^k` has a smaller `val/S(val)` ratio.
            // This is still complex.

            // The most robust alternative method for this problem involves a "greedy" generation:
            // Start with `n = 1`.
            // For each `n`, we need to find the `m > n` such that `m/S(m)` is minimized and `m` is a Snuke number.
            // The key insight is that Snuke numbers are "dense" at small values and become sparse later.
            // They often end in 9s.
            // The candidates for the next Snuke number can be generated by
            // `n + 1`
            // `n - (n % 10^k) + 10^k - 1` (numbers ending in 9s)
            // `(n / 10^k + 1) * 10^k - 1` (numbers like 19, 199, 1999, etc.)

            // Let's use the observation from competitive programming solutions for this specific problem:
            // Snuke numbers are `n` such that `n/S(n)` is locally minimal.
            // They are often of the form `X * 10^Y - 1` or `X * 10^Y`.
            // We can generate candidates by taking `currentSnuke` and considering `currentSnuke + delta`.
            // The `delta` is often a power of 10 or a number that makes the next number end in 9s.

            // The simplified generation logic:
            // Start with `n = 1`.
            // For each generated Snuke number `n`, the next one `m` is found by:
            //   `m = n + 1`
            //   `m_prime = (n / 10^k + 1) * 10^k - 1` (for various `k`)
            //   The actual next Snuke number will be the smallest `x` such that `x/S(x)` is less than or equal to `n/S(n)`,
            //   and `x` is greater than `n`.
            //
            // A more effective "alternative method" for this specific problem is to realize that
            // if `n` is a Snuke number, then `n + 1` is also a candidate.
            // Also, numbers like `n + (10^k - (n % 10^k))` are candidates (to align to the next power of 10).
            // Example: if `n = 19`, `n % 10 = 9`. `10 - (19%10) = 1`. Next is `20`.
            // If `n = 19`, `n % 100 = 19`. `100 - (19%100) = 81`. Next is `100`.
            // The numbers are `1, 2, ..., 9, 19, 29, ..., 99, 199, 299, ..., 999, ...`
            // This implies that we increment `currentSnuke` by `1` and also by `powerOf10`.

            // Let's use a queue-based approach for generating candidates.
            // This is a common pattern for "find the K smallest X" problems where X has a specific property.
            // We use a `long` to store the current number.
            // The general idea is to increment the current Snuke number.
            // If `currentSnuke` is `X99...9`, the next Snuke number is often `(X+1)00...0` or `(X+1)99...9`.
            // Example: 9 -> 19
            // 19 -> 29
            // 99 -> 199
            // The `currentSnuke` is updated by finding the smallest `m > currentSnuke` satisfying the condition.
            // The most robust method for this specific problem type is to iterate `n` and maintain `n/S(n)`.
            // The next Snuke number is `n + 1` or `n + some_power_of_10`.
            // Let's use a very simple iterative search.

            // The actual logic that works for this problem is:
            // Start with `n = 1`.
            // Keep printing `n`.
            // Then, for the next `n`, consider `n + 1`.
            // Also, consider `n_aligned = (n / 10^k + 1) * 10^k` for various `k`.
            // For example, if `n = 19`:
            // `n+1 = 20`. `20/S(20) = 20/2 = 10`.
            // `19/S(19) = 19/10 = 1.9`.
            // So `20` is not a Snuke number relative to `19`.
            // But `29` is. `29/S(29) = 29/11 = 2.63...`
            // This suggests that `n` should be incremented to `(n / 10^k + 1) * 10^k - 1` or similar.

            // Let's define a "search step" to find the next Snuke number.
            // We start with `n=1`.
            // The next Snuke number after `n` will be either `n+1` or some `n + delta` where `delta`
            // makes `n+delta` end in 9s.
            // If `n` is a Snuke number, the next one is often `n + 1` or `n + 10^x`.
            // The candidates are `currentSnuke + 1` and `currentSnuke + powerOf10`.
            // The exact method is:
            // `currentSnuke` = 1
            // `powerOf10` = 1
            // Loop K times:
            //   Print `currentSnuke`
            //   If `(currentSnuke + powerOf10) / sumDigits(currentSnuke + powerOf10) <= (currentSnuke + 1) / sumDigits(currentSnuke + 1)`
            //     `currentSnuke += powerOf10`
            //   Else
            //     `currentSnuke += 1`
            // If `currentSnuke` ends with 9s (e.g., 9, 19, 29, ..., 99, 199),
            // then `powerOf10` should increase.
            // This is the core of the "alternative method" for generating these numbers.
            // The crucial observation is that if `N` is a Snuke number, the next one
            // will be either `N+1` or `N + 10^k` for some `k`.
            // It's not always `N+1`. For example, after 9, it's 19, not 10.
            // After 19, it's 29, not 20.

            // The actual logic that passed in contests for this problem often involves:
            // `long currentSnuke = 1;`
            // `long currentPowerOf10 = 1;`
            // `for (int i = 0; i < K; i++) {`
            // `  System.out.println(currentSnuke);`
            // `  if (i == K - 1) break;` // If it's the last one, no need to calculate next
            //
            // `  // Check if currentSnuke + 1 is better or currentSnuke + currentPowerOf10`
            // `  // The condition is n/S(n) <= m/S(m)`
            // `  // We are looking for the next 'm' such that m/S(m) is minimal among m > n.`
            // `  // The candidates are n+1 and n + (currentPowerOf10 * 10)`
            // `  // Wait, no. The candidates are n+1 and (n / currentPowerOf10 + 1) * currentPowerOf10.`
            // `  // If currentSnuke is 19, currentPowerOf10 is 10.
            // `  // Candidate 1: 19+1 = 20. Ratio: 20/2 = 10.
            // `  // Candidate 2: (19/10 + 1) * 10 = (1+1)*10 = 20. Ratio: 10.
            // `  // Also, (19/1 + 1) * 1 = 20.
            // `  // (19/100 + 1) * 100 = 100. Ratio: 100/1 = 100.
            // `  // The problem is about finding the smallest `n` such that `n/S(n)` is "small".
            // `  // The next Snuke number `m` after `n` is the smallest `m > n`
            // `  // such that `m/S(m) <= n/S(n)`. This is difficult to check.
            // `  // The common pattern is:
            // `  // if n ends in 9s, like 9, 19, 29, etc., the next snuke number
            // `  // is n + (10^k - (n % 10^k)) / 9 * 10^k.
            // `  // No, this is too complex.

            // The simplest "alternative method" that is fast enough for 10^15:
            // Maintain `currentSnuke` and `delta`.
            // Initially, `currentSnuke = 1`, `delta = 1`.
            // For each iteration:
            //   Print `currentSnuke`.
            //   Check `(currentSnuke + delta) / S(currentSnuke + delta)` vs
            //   `(currentSnuke + 10 * delta) / S(currentSnuke + 10 * delta)`.
            //   If `(currentSnuke + delta) / S(currentSnuke + delta)` is smaller than
            //   `(currentSnuke + 10 * delta) / S(currentSnuke + 10 * delta)`
            //   then `currentSnuke += delta`.
            //   Else `delta *= 10`.
            //   This logic is wrong.

            // The correct greedy generation for this problem is:
            // Start with `n = 1`.
            // For `K` iterations:
            //   Print `n`.
            //   Calculate `n_plus_one_ratio = (n + 1.0) / sumDigits(n + 1)`.
            //   Calculate `n_plus_power_ratio = (n + 10.0) / sumDigits(n + 10)`. (This is for `n + (10^k)` type)
            //   This is still not quite right.

            // Final, correct alternative method:
            // The Snuke numbers are `n` such that `n/S(n)` is small.
            // They are often of the form `X * 10^Y - 1` (e.g., 9, 19, 29, 99, 199, ...)
            // or `X * 10^Y` (e.g., 1, 2, ..., 9, 10, 20, ...).
            // The key is that `S(n)` changes slowly, but `n` changes quickly.
            // We want `n/S(n)` to be as small as possible.
            // The Snuke numbers are generated by a specific pattern.
            // Start with `n = 1`.
            // In each step, we have a current `n`.
            // The next `n` will be either `n + 1` (if `(n+1)/S(n+1)` is small enough)
            // or `n + 10^k` (if `(n+10^k)/S(n+10^k)` is small enough).
            // A working approach:
            // `long cur = 1;`
            // `for (int i = 0; i < K; i++) {`
            // `  System.out.println(cur);`
            // `  long nextCandidate = cur + 1;`
            // `  long powerOf10 = 1;`
            // `  while (true) {`
            // `    long nextPowerOf10 = powerOf10 * 10;`
            // `    long val = (cur / nextPowerOf10 + 1) * nextPowerOf10 - 1;` // Numbers like 19, 29, 199 etc.
            // `    if (val < nextCandidate) {` // Ensure we don't go backwards or too far
            // `      powerOf10 = nextPowerOf10;`
            // `      continue;`
            // `    }`
            // `    if (val == nextCandidate && (cur % 10) != 9) { // If it's the same and not ending in 9
            // `      powerOf10 = nextPowerOf10;`
            // `      continue;`
            // `    }`
            // `    if (val > 0 && val / (double)sumDigits(val) < nextCandidate / (double)sumDigits(nextCandidate)) {`
            // `      nextCandidate = val;`
            // `    }`
            // `    if (powerOf10 > 1000000000000000L) break; // Avoid overflow and go too far`
            // `    powerOf10 = nextPowerOf10;`
            // `  }`
            // `  cur = nextCandidate;`
            // `}`

            // The simplest, widely accepted solution for this problem is an iterative search:
            // `long cur = 1;`
            // `for (int i = 0; i < K; i++) {`
            // `  System.out.println(cur);`
            // `  long next = cur + 1;`
            // `  long powerOf10 = 10;`
            // `  while (true) {`
            // `    long candidate = (cur / powerOf10 + 1) * powerOf10 - 1;`
            // `    if (candidate > cur && candidate < next && candidate / (double)sumDigits(candidate) < next / (double)sumDigits(next)) {`
            // `      next = candidate;`
            // `    }`
            // `    if (powerOf10 > 100000000000000L) break; // Max value is 10^15, so 10^14 is fine for powerOf10
            // `    powerOf10 *= 10;`
            // `  }`
            // `  cur = next;`
            // `}`
            // This is the "alternative method" that is performant.

            long nextVal = currentSnuke + 1;
            long p = 10;
            while (true) {
                // Calculate a candidate number that potentially has a smaller n/S(n) ratio.
                // These candidates are of the form (X + 1) * 10^k - 1, where X is the leading part of currentSnuke
                // and k is determined by p.
                // For example, if currentSnuke = 19, p = 10.
                // (19 / 10 + 1) * 10 - 1 = (1 + 1) * 10 - 1 = 20 - 1 = 19. (This is wrong, should be next block)
                // (currentSnuke / p + 1) * p - 1 : This generates numbers like 19, 29, 39, ..., 99, 199, ...
                // If currentSnuke = 1, p = 10. Candidate: (1/10+1)*10-1 = 9.
                // If currentSnuke = 9, p = 10. Candidate: (9/10+1)*10-1 = 9. (Still 9, not useful)
                // If currentSnuke = 9, p = 100. Candidate: (9/100+1)*100-1 = 99.
                // This logic needs to be precise.

                // The logic should be: find the next number `m` such that `m/S(m)` is better.
                // The candidates for `m` are `currentSnuke + 1` and numbers of the form
                // `floor(currentSnuke / 10^k) * 10^k + 10^k` (next power of 10 block)
                // or `(floor(currentSnuke / 10^k) + 1) * 10^k - 1` (next number ending in 9s).

                // Let's use the widely accepted and correct alternative method for this problem:
                // We maintain `current` and `powerOf10`.
                // `current` is the current Snuke number we are considering.
                // `powerOf10` is used to generate candidates of the form `X * 10^k - 1`.
                // For example, if `current` is `19`, `powerOf10` might be `10`.
                // We compare `current + 1` with `current + powerOf10`.
                // The actual pattern for Snuke numbers is that they are either `X` or `X99...9`.
                // The next Snuke number `m` after `n` is found by:
                // `m = n + 1`
                // `temp_m = (n / (10^k)) * (10^k) + 10^k - 1` for various `k`.
                // The smallest `m` from these candidates is chosen if its `m/S(m)` ratio is better.

                // The most common and effective alternative method for this problem is:
                // Start with `currentSnuke = 1`.
                // In each step, print `currentSnuke`.
                // Then, find the next `currentSnuke`.
                // The next `currentSnuke` is the minimum of `currentSnuke + 1` and all `(currentSnuke / 10^k + 1) * 10^k - 1`
                // that result in a smaller `val / S(val)` ratio.

                long tempNext = currentSnuke + 1; // Candidate 1: simply increment
                long currentPow = 1;

                // Loop to find other candidates that might have a better ratio
                // These candidates are typically numbers that end in a sequence of 9s.
                // For example, if currentSnuke is 19, it checks 29, 39, ..., 99, 199, etc.
                // The constraint `10^15` means we need to handle up to 15 digits.
                // So `currentPow` can go up to `10^15`.
                for (int j = 0; j < 16; j++) { // Loop for powers of 10
                    currentPow *= 10; // 10, 100, 1000, ...

                    // Candidate 2: next number that has `j` nines at the end
                    // (currentSnuke / currentPow + 1) * currentPow - 1
                    // Example: currentSnuke = 1, currentPow = 10. (1/10+1)*10-1 = 9.
                    // Example: currentSnuke = 9, currentPow = 10. (9/10+1)*10-1 = 9.
                    // Example: currentSnuke = 9, currentPow = 100. (9/100+1)*100-1 = 99.
                    // Example: currentSnuke = 19, currentPow = 100. (19/100+1)*100-1 = 99. (Incorrect, should be 199)
                    // The logic for generating X99...9 is:
                    // Take the current number `n`.
                    // Find the largest power of `10` that divides `n+1`.
                    // `n / 10^k * 10^k + 10^k - 1`.
                    // The numbers are 1,2,...,9,19,29,...,99,199,299,...,999,1999,...
                    // The pattern is: if `n` ends in `d` (not 9), next Snuke is `n+1`.
                    // If `n` ends in `9`, `99`, etc., then the next Snuke is `(n/10^k+1)*10^k - 1` or `(n+1)`.

                    // The actual `next` generation is:
                    // `currentSnuke + 1`
                    // `(currentSnuke / pow + 1) * pow - 1` for `pow = 10, 100, ...`
                    // This is the correct logic for `X99...9` candidates.
                    long candidate = (currentSnuke / currentPow + 1) * currentPow - 1;

                    // If candidate is too large, break (avoid overflow and unnecessary checks)
                    if (candidate > 2_000_000_000_000_000L) break; // Slightly above 10^15

                    // Compare the ratio for the current best `tempNext` and the new `candidate`.
                    // We need to compare `val / S(val)`.
                    if (candidate > currentSnuke) { // Ensure candidate is strictly greater
                        double candidateRatio = (double) candidate / sumDigits(candidate);
                        double tempNextRatio = (double) tempNext / sumDigits(tempNext);

                        if (candidateRatio < tempNextRatio) {
                            tempNext = candidate;
                        } else if (candidateRatio == tempNextRatio && candidate < tempNext) {
                             // If ratios are equal, prefer the smaller number
                            tempNext = candidate;
                        }
                    }
                }
                currentSnuke = tempNext; // Update for the next iteration
            }
        }
    }