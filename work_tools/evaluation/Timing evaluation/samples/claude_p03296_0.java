import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        
        int spells = 0;
        int i = 0;
        
        while (i < n) {
            int j = i;
            // Find the end of current group of same colors
            while (j < n && a[j] == a[i]) {
                j++;
            }
            
            int groupSize = j - i;
            // For a group of size k, we need floor(k/2) spells
            spells += groupSize / 2;
            
            i = j;
        }
        
        System.out.println(spells);
        sc.close();
    }
}