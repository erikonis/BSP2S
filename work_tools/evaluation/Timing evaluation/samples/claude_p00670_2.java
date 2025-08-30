import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            int n = sc.nextInt();
            int S = sc.nextInt();
            
            if (n == 0 && S == 0) break;
            
            int[] r = new int[n];
            for (int i = 0; i < n; i++) {
                r[i] = sc.nextInt();
            }
            
            int count = 0;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (r[i] + r[j] > S) {
                        count++;
                    }
                }
            }
            
            System.out.println(count);
        }
        
        sc.close();
    }
}