import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String S = sc.next();
        String target = "CODEFESTIVAL2016";
        int cnt = 0;
        for (int i = 0; i < 16; i++) {
            if (S.charAt(i) != target.charAt(i)) {
                cnt++;
            }
        }
        System.out.println(cnt);
    }
}