import java.util.Scanner;

public class BeautifulString {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String w = scanner.nextLine();
        int[] counts = new int[26]; // To store counts of each letter
        
        for (char c : w.toCharArray()) {
            counts[c - 'a']++;
        }
        
        boolean isBeautiful = true;
        for (int count : counts) {
            if (count % 2 != 0) {
                isBeautiful = false;
                break;
            }
        }
        
        System.out.println(isBeautiful ? "Yes" : "No");
    }
}