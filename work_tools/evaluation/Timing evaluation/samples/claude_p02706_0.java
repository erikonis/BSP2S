import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(); // vacation days
        int M = sc.nextInt(); // number of assignments
        
        int totalAssignmentDays = 0;
        for (int i = 0; i < M; i++) {
            totalAssignmentDays += sc.nextInt();
        }
        
        if (totalAssignmentDays > N) {
            System.out.println(-1);
        } else {
            System.out.println(N - totalAssignmentDays);
        }
        
        sc.close();
    }
}