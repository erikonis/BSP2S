import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        int maxLength = 0;
        int currentLength = 0;

        for (char c : s.toCharArray()) {
            if (c == 'A' || c == 'C' || c == 'G' || c == 'T') {
                currentLength++;
            } else {
                maxLength = Math.max(maxLength, currentLength);
                currentLength = 0;
            }
        }
        maxLength = Math.max(maxLength, currentLength); // Check after loop for trailing ACGT string
        System.out.println(maxLength);
        sc.close();
    }
}