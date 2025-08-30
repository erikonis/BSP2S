import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int totalSum = 0;
        Pattern p = Pattern.compile("\\d+"); // Matches one or more digits

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            Matcher m = p.matcher(line);
            while (m.find()) {
                totalSum += Integer.parseInt(m.group());
            }
        }
        System.out.println(totalSum);
        sc.close();
    }
}