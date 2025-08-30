import java.util.Scanner;

public class TogglingCases {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        StringBuilder res = new StringBuilder(s.length());
        for (char c : s.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                res.append((char)(c + 32));
            } else if (c >= 'a' && c <= 'z') {
                res.append((char)(c - 32));
            } else {
                res.append(c);
            }
        }
        System.out.println(res.toString());
    }
}