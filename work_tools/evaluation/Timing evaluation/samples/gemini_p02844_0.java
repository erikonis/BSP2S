import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        String S = sc.next();
        sc.close();

        Set<String> pinCodes = new HashSet<>();

        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                for (int k = j + 1; k < N; k++) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(S.charAt(i));
                    sb.append(S.charAt(j));
                    sb.append(S.charAt(k));
                    pinCodes.add(sb.toString());
                }
            }
        }

        System.out.println(pinCodes.size());
    }
}