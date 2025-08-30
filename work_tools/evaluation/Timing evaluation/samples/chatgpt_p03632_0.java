import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int A = sc.nextInt();
        int B = sc.nextInt();
        int C = sc.nextInt();
        int D = sc.nextInt();

        // Find overlap between [A, B) and [C, D)
        int overlapStart = Math.max(A, C);
        int overlapEnd = Math.min(B, D);

        int result = Math.max(0, overlapEnd - overlapStart);
        System.out.println(result);
    }
}