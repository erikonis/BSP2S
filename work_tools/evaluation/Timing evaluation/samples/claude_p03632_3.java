import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int A = sc.nextInt();
        int B = sc.nextInt();
        int C = sc.nextInt();
        int D = sc.nextInt();
        
        // Find intersection of intervals [A,B] and [C,D]
        int start = Math.max(A, C);
        int end = Math.min(B, D);
        
        // If start >= end, no overlap exists
        int overlap = Math.max(0, end - start);
        
        System.out.println(overlap);
        sc.close();
    }
}