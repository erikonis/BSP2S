import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.equals("END OF INPUT")) break;

            String[] words = line.split(" ", -1); // -1 to preserve trailing empty strings
            StringBuilder sb = new StringBuilder();
            for (String word : words) {
                sb.append(word.length());
            }
            System.out.println(sb);
        }
    }
}