import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            int joiCount = 0;
            int ioiCount = 0;
            
            // Check each possible 3-character substring
            for (int i = 0; i <= line.length() - 3; i++) {
                String substring = line.substring(i, i + 3);
                if (substring.equals("JOI")) {
                    joiCount++;
                } else if (substring.equals("IOI")) {
                    ioiCount++;
                }
            }
            
            System.out.println(joiCount);
            System.out.println(ioiCount);
        }
        
        scanner.close();
    }
}