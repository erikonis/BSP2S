import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int L = scanner.nextInt();
        int A = scanner.nextInt();
        int B = scanner.nextInt();
        int C = scanner.nextInt();
        int D = scanner.nextInt();

        // Calculate days needed for each subject
        int japaneseDays = (int) Math.ceil((double) A / C);
        int mathDays = (int) Math.ceil((double) B / D);
        
        // Maximum days needed to complete both
        int studyDays = Math.max(japaneseDays, mathDays);
        
        // Maximum play days
        int playDays = L - studyDays;
        
        System.out.println(playDays);
    }
}