import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        
        // Check if palindrome is possible
        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }
        
        int oddCount = 0;
        for (int f : freq) {
            if (f % 2 == 1) oddCount++;
        }
        
        // At most one character can have odd frequency for palindrome
        if (oddCount > 1) {
            System.out.println(-1);
            return;
        }
        
        char[] arr = s.toCharArray();
        int n = arr.length;
        int swaps = 0;
        
        // For each position from start, find its matching character from end
        for (int i = 0; i < n / 2; i++) {
            int left = i;
            int right = n - 1 - i;
            
            // Find matching character for arr[left] starting from right
            while (left < right && arr[left] != arr[right]) {
                right--;
            }
            
            // Move the matching character to its correct position
            while (right < n - 1 - i) {
                // Swap with next character
                char temp = arr[right];
                arr[right] = arr[right + 1];
                arr[right + 1] = temp;
                right++;
                swaps++;
            }
        }
        
        System.out.println(swaps);
    }
}