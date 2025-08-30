import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int targetHours = sc.nextInt();
            if (targetHours == 0) {
                break;
            }

            int numStudies = sc.nextInt();
            int totalStudyHours = 0;

            for (int i = 0; i < numStudies; i++) {
                int start = sc.nextInt();
                int end = sc.nextInt();
                totalStudyHours += (end - start);
            }

            if (totalStudyHours >= targetHours) {
                System.out.println("OK");
            } else {
                System.out.println(targetHours - totalStudyHours);
            }
        }
        sc.close();
    }
}