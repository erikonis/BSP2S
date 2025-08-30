import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        while (sc.hasNext()) {
            int year = sc.nextInt();
            int month = sc.nextInt();
            int day = sc.nextInt();
            
            String result = convertToWareki(year, month, day);
            System.out.println(result);
        }
        
        sc.close();
    }
    
    private static String convertToWareki(int year, int month, int day) {
        // Check if date is before Meiji era
        if (year < 1868 || (year == 1868 && month < 9) || (year == 1868 && month == 9 && day < 8)) {
            return "pre-meiji";
        }
        
        // Check Heisei era (1989.1.8 ~)
        if (year > 1989 || (year == 1989 && month > 1) || (year == 1989 && month == 1 && day >= 8)) {
            int heiseiYear = year - 1989 + 1;
            return "heisei " + heiseiYear + " " + month + " " + day;
        }
        
        // Check Showa era (1926.12.25 ~ 1989.1.7)
        if (year > 1926 || (year == 1926 && month == 12 && day >= 25)) {
            int showaYear = year - 1926 + 1;
            return "showa " + showaYear + " " + month + " " + day;
        }
        
        // Check Taisho era (1912.7.30 ~ 1926.12.24)
        if (year > 1912 || (year == 1912 && month > 7) || (year == 1912 && month == 7 && day >= 30)) {
            int taishoYear = year - 1912 + 1;
            return "taisho " + taishoYear + " " + month + " " + day;
        }
        
        // Meiji era (1868.9.8 ~ 1912.7.29)
        int meijiYear = year - 1868 + 1;
        return "meiji " + meijiYear + " " + month + " " + day;
    }
}