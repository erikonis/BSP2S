import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < n; i++) {
            String line = sc.nextLine();
            StringBuilder sb = new StringBuilder();
            int idx = 0;
            while (idx < line.length()) {
                if (idx + 7 <= line.length() && line.substring(idx, idx + 7).equals("Hoshino")) {
                    sb.append("Hoshina");
                    idx += 7;
                } else {
                    sb.append(line.charAt(idx));
                    idx++;
                }
            }
            System.out.println(sb.toString());
        }
    }
}