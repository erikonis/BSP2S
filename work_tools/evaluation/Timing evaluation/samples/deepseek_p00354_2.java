import java.util.Scanner;

public class DayOfWeek {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int X = scanner.nextInt();
        
        // September 1, 2017 is Friday (fri)
        String[] days = {"fri", "sat", "sun", "mon", "tue", "wed", "thu"};
        
        // Calculate the day of the week
        int index = (X - 1) % 7;
        System.out.println(days[index]);
    }
}