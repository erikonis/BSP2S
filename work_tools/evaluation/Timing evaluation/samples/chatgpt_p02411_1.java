import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int m = sc.nextInt();
            int f = sc.nextInt();
            int r = sc.nextInt();
            if (m == -1 && f == -1 && r == -1) break;

            String grade;
            if (m == -1 || f == -1) {
                grade = "F";
            } else {
                int total = m + f;
                if (total >= 80) {
                    grade = "A";
                } else if (total >= 65) {
                    grade = "B";
                } else if (total >= 50) {
                    grade = "C";
                } else if (total >= 30) {
                    if (r >= 50) {
                        grade = "C";
                    } else {
                        grade = "D";
                    }
                } else {
                    grade = "F";
                }
            }
            System.out.println(grade);
        }
        sc.close();
    }
}