import java.util.*;

public class Main {
    // Mapping for each button
    static String[] buttonMap = new String[10];
    static {
        buttonMap[1] = ".,!? ";
        buttonMap[2] = "abc";
        buttonMap[3] = "def";
        buttonMap[4] = "ghi";
        buttonMap[5] = "jkl";
        buttonMap[6] = "mno";
        buttonMap[7] = "pqrs";
        buttonMap[8] = "tuv";
        buttonMap[9] = "wxyz";
        buttonMap[0] = ""; // 0 is confirm
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = Integer.parseInt(sc.nextLine());
        for (int tc = 0; tc < T; tc++) {
            String s = sc.nextLine().trim();
            StringBuilder sb = new StringBuilder();
            char prev = ' ';
            int cnt = 0;
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);
                if (ch == '0') {
                    // Confirm: output if there is previous button presses
                    if (cnt > 0 && prev != '0') {
                        int btn = prev - '0';
                        int len = buttonMap[btn].length();
                        int idx = (cnt - 1) % len;
                        sb.append(buttonMap[btn].charAt(idx));
                    }
                    // Reset for next char
                    cnt = 0;
                    prev = '0';
                } else {
                    if (ch == prev) {
                        cnt++;
                    } else {
                        cnt = 1;
                        prev = ch;
                    }
                }
            }
            System.out.println(sb.toString());
        }
    }
}