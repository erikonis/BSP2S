import java.util.*;

public class Main {
    public static void main(String[] args) {
        try(Scanner scn = new Scanner(System.in)) {
            int n, k;
            while((n = scn.nextInt()) > 0 | (k = scn.nextInt()) > 0) {
                int[] s = new int[k];
                for(int i = 0; i < k; i++) {
                    s[i] = scn.nextInt();
                }
                for(int i = 0; i < n; i++) {
                    for(int j = 0; j < k; j++) {
                        s[j] -= scn.nextInt();
                    }
                }
                Arrays.sort(s);
                System.out.println(s[0] < 0 ? "No" : "Yes");
            }
        }
    }
}