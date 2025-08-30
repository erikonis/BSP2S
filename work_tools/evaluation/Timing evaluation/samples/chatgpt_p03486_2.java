import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        String t = sc.next();

        // s' : smallest lexicographically -> sort ascending
        char[] sArr = s.toCharArray();
        Arrays.sort(sArr);

        // t' : biggest lexicographically -> sort descending
        char[] tArr = t.toCharArray();
        Arrays.sort(tArr);
        reverse(tArr);

        String sPrime = new String(sArr);
        String tPrime = new String(tArr);

        if (sPrime.compareTo(tPrime) < 0) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }

    // Reverse a char array in-place
    static void reverse(char[] arr) {
        int n = arr.length;
        for (int i = 0; i < n / 2; i++) {
            char tmp = arr[i];
            arr[i] = arr[n - 1 - i];
            arr[n - 1 - i] = tmp;
        }
    }
}