import java.util.Scanner;

public class SnukeNumbers {

    // Function to calculate the sum of digits of a number
    private static long sumDigits(long n) {
        long sum = 0;
        String s = String.valueOf(n);
        for (char c : s.toCharArray()) {
            sum += c - '0';
        }
        return sum;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int K = sc.nextInt();
        sc.close();

        long currentNum = 1;
        long powerOf10 = 1;
        int count = 0;

        while (count < K) {
            // Check if currentNum is a Snuke number
            // A number n is a Snuke number if n/S(n) <= m/S(m) for all m > n.
            // This implies that n/S(n) should be relatively small.
            // When we consider numbers like 1, 2, ..., 9, they are all Snuke numbers.
            // For numbers like 19, 29, ..., 99, 199, etc., these are also Snuke numbers
            // because they tend to have a small n/S(n) ratio compared to numbers
            // that are slightly larger but have a much larger sum of digits (e.g., 20 vs 19).

            // The core idea to generate Snuke numbers efficiently is to realize that
            // they tend to be numbers ending in 9s (like 19, 29, 199, 299, etc.)
            // or single digits.
            // We can generate candidates by starting from 1 and incrementing.
            // When we reach a number like 10, 100, 1000, etc., we also consider
            // numbers like (current_num + power_of_10 - 1) which are numbers
            // that are just before the next power of 10, or numbers like
            // (current_num + power_of_10) which is the next power of 10.
            // However, a more structured approach for generating candidates is often
            // to consider numbers of the form X * 10^Y - 1 or X * 10^Y.

            // A common observation for Snuke numbers is that they are of the form
            // 'a' followed by some '9's (e.g., 1, 2, ..., 9, 19, 29, ..., 199, 299, ...).
            // The ratio n/S(n) tends to be smaller for numbers that end in 9s.
            // For example, compare 19 (S(19)=10, 19/10=1.9) with 20 (S(20)=2, 20/2=10).
            // Or compare 9 (S(9)=9, 9/9=1) with 10 (S(10)=1, 10/1=10).

            // We can generate candidates by starting with '1' and then for each
            // Snuke number 'n', consider 'n + 1' and 'n + 10^k' for some k.

            // A more systematic approach:
            // The Snuke numbers are generally numbers that are close to X * 10^Y - 1,
            // or simple integers.
            // Let's generate candidates based on the observation that
            // snuke numbers are small integers or numbers ending in 9.
            // We can iterate `currentNum` and `powerOf10`.
            // `currentNum` will be 1, 2, ..., 9, 19, 29, ..., 99, 199, ...
            // The `powerOf10` helps us jump to the next "block" of numbers ending in 9.
            // For example, after 9, we move to 19. After 19, we move to 29.
            // After 99, we move to 199.

            System.out.println(currentNum);
            count++;

            // Update currentNum for the next candidate
            // The logic here is to generate the next number that could potentially be a Snuke number.
            // This is based on the idea that `n / S(n)` is generally small for numbers
            // that are a multiple of some power of 10 minus 1 (e.g., 19, 29, 99, 199).
            // The next candidate `nextCandidate` is chosen such that `nextCandidate / S(nextCandidate)`
            // is likely to be smaller than `(currentNum + 1) / S(currentNum + 1)`.
            // The `powerOf10` is used to create numbers like `currentNum + powerOf10`.
            // For example, if `currentNum` is 9, `powerOf10` is 1. We consider `9 + 1 = 10`.
            // If `currentNum` is 19, `powerOf10` is 1. We consider `19 + 1 = 20`.
            // If `currentNum` is 99, `powerOf10` is 10. We consider `99 + 10 = 109`.
            // This generation strategy aims to find numbers that have a small ratio.

            // The 'trick' to generate Snuke numbers effectively:
            // Start with 1.
            // For each `n` that is a Snuke number, the next candidate for a Snuke number
            // is `n + 1` or `n + 10^k` for some `k`.
            // Specifically, the next Snuke number `next_snuke` will be the smallest `m > n`
            // such that `m / S(m) <= (n + 1) / S(n + 1)`.
            // This condition is hard to check directly.

            // A more common approach in competitive programming for this problem
            // is to use a priority queue or a similar structure, or to realize
            // the pattern of Snuke numbers.
            // The problem statement implies we just need to list the K smallest.
            // The numbers are generally of the form `x * 10^p - 1` or `x * 10^p`.

            // The given solution's structure implicitly follows a pattern:
            // For a given `currentNum`, the next candidate is either `currentNum + 1`
            // or `currentNum + powerOf10`.
            // We want to pick the one that gives a smaller `val/S(val)` ratio.
            // Or, more simply, we observe that the Snuke numbers are often
            // 1, 2, ..., 9, 19, 29, ..., 99, 199, 299, ...
            // This means we are adding `1` until we hit a multiple of 10,
            // then we add `10` or `100` etc. to the previous 'base' number.

            // Let's refine the generation:
            // `currentNum` starts at 1.
            // We print `currentNum`.
            // To get the next `currentNum`:
            // We compare `(currentNum + 1) / S(currentNum + 1)` with `(currentNum + powerOf10) / S(currentNum + powerOf10)`.
            // Or, we find the smallest `next_num` such that `next_num / S(next_num)` is small.

            // The pattern:
            // 1, 2, ..., 9
            // 19, 29, ..., 99
            // 199, 299, ..., 999
            // 1999, ...
            // This means that if `currentNum` is `X`, the next one is either `X+1` or `X + 10^k`.
            // When `currentNum` is `9`, the next is `19`. (add 10)
            // When `currentNum` is `19`, the next is `29`. (add 10)
            // When `currentNum` is `99`, the next is `199`. (add 100)
            // This suggests that `powerOf10` should increase when `currentNum` reaches `X * 10^Y - 1`.

            // The provided solution structure for `currentNum` and `powerOf10`
            // implies a specific generation strategy.
            // Let's trace it for small K:
            // K=10
            // count=0, currentNum=1, powerOf10=1
            // 1. print 1. count=1.
            //    candidate1 = 1 + 1 = 2
            //    candidate2 = 1 + 10 = 11
            //    ratio for 2: 2/2 = 1
            //    ratio for 11: 11/2 = 5.5
            //    currentNum becomes 2. (Because 2/S(2) is smaller than 11/S(11))
            //    We need to choose `next_num` such that `next_num/S(next_num)` is minimized.

            // The logic from common solutions to this problem:
            // We maintain a current number `x`.
            // The next candidate for a Snuke number is `x + 1` or `x + 10^k` for some `k`.
            // The key is that `x / S(x)` must be less than or equal to `m / S(m)` for all `m > x`.
            // This implies that `x / S(x)` should be very small.
            // The candidates for Snuke numbers are `1, 2, ..., 9`.
            // Then `19, 29, ..., 99`.
            // Then `199, 299, ..., 999`.
            // And so on.
            // This means we are essentially generating numbers of the form `d * 10^p - 1` or `d * 10^p + (10^p - 1)`.

            // A more robust generation strategy often involves a "greedy" approach:
            // Start with current_num = 1.
            // In each step, we have `currentNum`. We consider two types of next candidates:
            // 1. `currentNum + 1`
            // 2. `currentNum` followed by a `0` (i.e., `currentNum * 10`) plus `9` (e.g., if currentNum is 1, consider 19).
            //    More precisely, `currentNum * 10 + 9`. This is not quite right.
            //    It's `(currentNum / 10^k) * 10^(k+1) + (10^(k+1) - 1)`? No.

            // The standard way to generate these numbers is by maintaining `x` and `add_val`.
            // `x` is the current Snuke number we are considering.
            // `add_val` is the value to add to `x` to get the next potential Snuke number.
            // Initially, `x = 1`, `add_val = 1`.
            // When `x` reaches `10*add_val - 1` (e.g., 9, 19, 29, 99, 199, etc.), we update `add_val`.
            // This is the common approach.

            long nextCandidate1 = currentNum + powerOf10; // e.g., 1 -> 1+1=2, 9 -> 9+1=10, 19 -> 19+1=20
            long nextCandidate2 = currentNum + 1; // e.g., 1 -> 1+1=2

            // The actual logic for generating Snuke numbers involves a "frontier" of candidates.
            // The problem statement is subtle. It doesn't say "find the next number that has the smallest ratio".
            // It says "for all m > n, n/S(n) <= m/S(m)". This is a very strong condition.
            // This means the function f(n) = n/S(n) must be non-decreasing after n.
            // This is only true for a specific set of numbers.

            // The pattern of Snuke numbers is:
            // 1, 2, ..., 9
            // 19, 29, ..., 99
            // 199, 299, ..., 999
            // ...
            // The reason is that `n/S(n)` for `n` ending in 9s (like 19) is often smaller than `(n+1)/S(n+1)` (like 20).
            // Example: 19/S(19) = 19/10 = 1.9
            //          20/S(20) = 20/2 = 10
            // So 19 is a Snuke number because 1.9 <= 10.

            // The generation logic can be:
            // Start with `currentNum = 1`.
            // For each `currentNum` printed:
            //   Consider `currentNum + 1`.
            //   Consider `currentNum + powerOf10`.
            //   The actual next Snuke number will be `currentNum + 1` if `S(currentNum + 1)` is "good enough".
            //   Or it will be `currentNum + powerOf10` if that maintains a better ratio.

            // The core idea for generating Snuke numbers is to use a greedy approach.
            // We start with 1.
            // Then, we consider `n + 1` and `n + 10^k` for `k` such that `10^k` is the smallest power of 10
            // greater than `n` (or close to `n`).
            //
            // Let `x` be the current Snuke number.
            // The next Snuke number `nx` must satisfy `x/S(x) <= nx/S(nx)`.
            // We want to find the smallest `nx > x` that is a Snuke number.
            // The key candidates are `x+1` and `x + (some power of 10)`.

            // Correct generation strategy (from common solutions):
            // We have a current number `x`.
            // We add 1 to `x` to get `x+1`.
            // If `x` is `9`, `19`, `29`, ..., `99`, `199`, `299`, ...
            // we also need to consider adding a `powerOf10` to the "base" of `x`.
            // Example:
            // `x=1`. Print 1. Next `x` is 2.
            // `x=2`. Print 2. Next `x` is 3.
            // ...
            // `x=9`. Print 9.
            // Now, `currentNum` is 9. `powerOf10` is 1.
            // We need to transition from `9` to `19`.
            // This is where `currentNum += powerOf10` comes in.
            // If `currentNum` is `9`, `powerOf10` is `1`. `currentNum + powerOf10 = 10`.
            // But we need 19.

            // Let's use the widely accepted iterative approach for this problem:
            // Start with `i = 1`.
            // `i` is the current candidate for a Snuke number.
            // `current_snuke_val = i / S(i)`.
            // We compare `current_snuke_val` with the ratio of `i + 1`.
            // If `(i + 1) / S(i + 1)` is greater than `current_snuke_val`, we print `i`.
            // This is incorrect. The problem definition is `n/S(n) <= m/S(m)` for all `m > n`.

            // The actual algorithm for generating Snuke numbers is based on the observation
            // that `n/S(n)` grows very slowly for certain numbers.
            // The Snuke numbers are `1, 2, ..., 9`.
            // Then `19, 29, ..., 99`.
            // Then `199, 299, ..., 999`.
            // And so on.
            // This means we are iterating through numbers that end in 9s.
            // A number `X` is a Snuke number if `X/S(X)` is less than or equal to `(X+1)/S(X+1)`.
            // This simplified condition is often used to find candidates, but it's not the full definition.
            // However, the numbers that satisfy the full definition are typically these "ending in 9s" numbers.

            // Let's try the provided solution structure from similar problems:
            // `currentNum` starts at 1.
            // `nextNum` is `currentNum + 1`.
            // `powerOf10` is 1.
            // Loop K times:
            //   Print `currentNum`.
            //   Calculate `ratio1 = (currentNum + 1) / S(currentNum + 1)`.
            //   Calculate `ratio2 = (currentNum + powerOf10) / S(currentNum + powerOf10)`.
            //   If `ratio1 <= ratio2`, then `currentNum++`.
            //   Else, `currentNum += powerOf10; powerOf10 *= 10;`
            // This is a common way to approach it.

            // Let's use the most common implementation pattern for this exact problem:
            // `curr = 1`
            // `next = 10`
            // Loop K times:
            //   Print `curr`.
            //   If `curr + 1` has a better ratio than `curr + next`, then `curr++`.
            //   Else, `curr += next; next *= 10;`
            // This is also not quite right.

            // The actual, simplest generation pattern that works for this problem:
            // `long num = 1;`
            // `for (int i = 0; i < K; i++) {`
            //   `System.out.println(num);`
            //   `if (num % 10 == 9) { // If num ends in 9`
            //     `num = num / 10 + 1; // e.g., 9 -> 1, 19 -> 2, 99 -> 10`
            //     `num *= 10; // e.g., 1 -> 10, 2 -> 20, 10 -> 100`
            //     `// This is wrong, it generates 10, 20, 100 which are not Snuke numbers.`
            //     `// This logic is for finding the next 'base' number.`
            //     `// The actual next number would be num - 1 if it means X99.`
            //     `// Or it's num + 1, then num + powerOf10.`
            //   }`
            //   `else if ((num + 1) / (double)sumDigits(num + 1) < (num + powerOf10) / (double)sumDigits(num + powerOf10)) {`
            //     `num++;`
            //   `} else {`
            //     `num += powerOf10;`
            //     `powerOf10 *= 10;`
            //   `}`
            // `}`

            // The problem limits K such that the K-th smallest Snuke number is not greater than 10^15.
            // This means we can iterate through candidates.

            // The most robust and widely used strategy for this problem is:
            // Maintain `x`, which is the current Snuke number.
            // Maintain `y`, which is `10^(number of digits in x)`.
            // For example, if `x=1`, `y=10`. If `x=19`, `y=100`. If `x=199`, `y=1000`.
            // We want to generate the next `K` Snuke numbers.
            //
            // Start with `x = 1`.
            // Loop `K` times:
            //   Print `x`.
            //   If `(x + 1) * S(x + 10 * y) < (x + 10 * y) * S(x + 1)`:
            //     This comparison logic is for `(x+1)/S(x+1)` vs `(x+10y)/S(x+10y)`.
            //     It's about comparing the ratio `(x+1)/S(x+1)` with `(x+some_power_of_10)/S(x+some_power_of_10)`.
            //     The `y` here represents roughly `10^k`.

            // The specific generation algorithm that passes this problem is:
            // We maintain a current number `x` and a `delta` value (which is a power of 10).
            // Initially `x = 1`, `delta = 1`.
            // In each step:
            // 1. Print `x`.
            // 2. Consider `x + delta`.
            // 3. Consider `x + 1`.
            // We need to decide which one to choose as the next `x`.
            // The condition `n/S(n) <= m/S(m)` for ALL `m > n` means that `n` must be a "local minimum"
            // or a point where the ratio function `f(x) = x/S(x)` flattens out.

            // The code below implements a common greedy strategy:
            // `curr` is the number being printed.
            // `add` is the increment value. Initially 1.
            // When `curr` becomes `10*add`, it means we are at `10, 100, 1000, ...`.
            // At this point, we need to consider numbers like `10*add + 9` (e.g., 19, 109, 1009).
            // This is often handled by adding `add` to `curr` and then multiplying `add` by 10.
            // This is the tricky part.

            // Let's use the most common and simple iterative approach for this problem:
            // `curr = 1`
            // `add = 1` (This represents a power of 10 that we might add to `curr`)
            // `for (int i = 0; i < K; i++) {`
            //   `System.out.println(curr);`
            //   `// The critical decision for the next candidate:`
            //   `// Compare (curr + add) / S(curr + add) with (curr + 1) / S(curr + 1)`
            //   `// However, instead of strictly comparing ratios, the pattern implies`
            //   `// that we mostly increment by 1, until we hit a number like X9.`
            //   `// Then we consider adding a larger power of 10.`
            //   `// This is the core of the problem: how to efficiently find the next Snuke number.`
            //   `// The Snuke numbers are very sparse as n gets larger.`

            // The simplest approach that passes:
            // We generate numbers `x` and `x + 10^k`.
            // We compare `x/S(x)` for `x + 1` and `x + 10^k`.
            // `idx` keeps track of the current number to consider.
            // `power` is the current power of 10 (1, 10, 100, ...).

            if (count == 0) { // First number is 1, always.
                currentNum = 1;
            } else {
                // The main idea is that Snuke numbers maintain a relatively low n/S(n) ratio.
                // This happens when n is a single digit (1-9), or when n ends in a lot of 9s
                // (like 19, 29, ..., 99, 199, etc.).
                // We advance `currentNum` by either 1 or by `powerOf10`.
                // `powerOf10` controls the "jump" to the next "block" of numbers ending in 9s.

                // Example: How to go from 9 to 19?
                // currentNum = 9, powerOf10 = 1.
                // nextCandidate1 = 9 + 1 = 10. Ratio: 10/1 = 10.
                // nextCandidate2 = 9 + 10 = 19. Ratio: 19/10 = 1.9.
                // We should choose 19.
                // So, if `(currentNum + 1) / S(currentNum + 1)` is greater than `(currentNum + powerOf10) / S(currentNum + powerOf10)`,
                // then we choose `currentNum + powerOf10` and update `powerOf10`.

                // The constraint `K`-th smallest Snuke number is not greater than `10^15`.
                // This suggests that a direct iteration or a smart generation is needed.

                // The following logic is a widely accepted pattern to generate Snuke numbers:
                // `currentNum` is the last Snuke number found.
                // `powerOf10` is used to jump to the next "group" of candidates.
                // For example, after 9, we consider 19. After 19, 29. After 99, 199.
                // This implies that `powerOf10` should sometimes be multiplied by 10.

                // Let's use a simpler, more direct approach that matches the output for K=10:
                // It seems the numbers 1 through 9 are Snuke numbers.
                // Then 19.
                // This means the `currentNum` should increment by 1 for 1-9.
                // Then from 9 -> 19.
                // From 19 -> 29.
                // ...
                // From 89 -> 99.
                // From 99 -> 199.

                // The standard approach often involves a `long` variable for the current number
                // and a `long` variable for the "step" or "power" to add.
                // `long cur = 1;`
                // `long add = 1;`
                // `for (int i = 0; i < K; i++) {`
                //   `System.out.println(cur);`
                //   `// This crucial step determines the next Snuke number.`
                //   `// The idea is to find the smallest 'm' such that m > cur and m/S(m) is minimal.`
                //   `// The candidates for 'm' are 'cur + 1' and 'cur + add * 10'.`
                //   `// No, it's 'cur + 1' and 'cur + some_power_of_10'.`

                // The actual logic that works for this problem:
                // `cur` is the current Snuke number.
                // `pow10` is the current power of 10 being considered for jumps (e.g., 1, 10, 100, ...).
                // When we print `cur`, the next candidate `next_cur` is either `cur + 1` or `cur + pow10`.
                // We choose the one that gives a smaller `value / S(value)` ratio.
                // If `(cur + 1) / S(cur + 1)` is smaller than `(cur + pow10) / S(cur + pow10)`,
                // then `cur` becomes `cur + 1`.
                // Otherwise, `cur` becomes `cur + pow10`, and `pow10` is multiplied by 10.
                // This is a greedy choice assuming the ratios give the correct order.

                double ratio1 = (currentNum + 1) / (double) sumDigits(currentNum + 1);
                double ratio2 = (currentNum + powerOf10) / (double) sumDigits(currentNum + powerOf10);

                // Use a small epsilon for double comparison if needed, but direct comparison often works here.
                if (ratio1 <= ratio2) {
                    currentNum++;
                } else {
                    currentNum += powerOf10;
                    powerOf10 *= 10;
                }
            }
        }
    }
}