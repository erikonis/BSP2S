import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }
        
        int m = sc.nextInt();
        int[] b = new int[m];
        for (int i = 0; i < m; i++) {
            b[i] = sc.nextInt();
        }
        
        // Compare lexicographically using built-in comparison
        String strA = "";
        String strB = "";
        
        for (int i = 0; i < n; i++) {
            strA += String.format("%04d", a[i]);
        }
        
        for (int i = 0; i < m; i++) {
            strB += String.format("%04d", b[i]);
        }
        
        if (strB.compareTo(strA) > 0) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }
        
        sc.close();
    }
}