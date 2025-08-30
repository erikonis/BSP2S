import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        int left = 0;
        int right = N;

        // Query the initial state of seat 0
        System.out.println(0);
        System.out.flush();
        String s0 = sc.next();
        if (s0.equals("Vacant")) {
            return;
        }

        // Query the initial state of seat N/2
        System.out.println(N / 2);
        System.out.flush();
        String s_N_half = sc.next();
        if (s_N_half.equals("Vacant")) {
            return;
        }

        // Binary search for an empty seat
        while (left < right) {
            int mid = left + (right - left) / 2;

            // Ensure mid is not the same as left or right to avoid infinite loops
            // if left and right are adjacent and both queried.
            if (mid == left) {
                mid = right; // If mid is same as left, try right to make progress
            }


            System.out.println(mid);
            System.out.flush();
            String s_mid = sc.next();
            if (s_mid.equals("Vacant")) {
                return;
            }

            // Determine if the empty seat is in the left or right half
            // based on the parity of the distance and the sex of the people.
            // If the sex of s0 and s_mid are different, and the distance (mid - 0) is odd,
            // or if the sex of s0 and s_mid are the same, and the distance (mid - 0) is even,
            // then there must be an empty seat in the range [0, mid].
            // Otherwise, it's in [mid, N].
            if ((s0.equals(s_mid) && (mid - 0) % 2 == 0) || (!s0.equals(s_mid) && (mid - 0) % 2 != 0)) {
                right = mid;
            } else {
                left = mid;
                s0 = s_mid; // Update the reference sex for the new range
            }
        }
    }
}