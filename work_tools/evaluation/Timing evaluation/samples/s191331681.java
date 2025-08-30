import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.next();

        String[] strArray = str.split("");
        String[] akibaArray = { "A", "K", "I", "H", "A", "B", "A", "R", "A" };
        boolean flag = true;
        int count = 0;
        int i = 0;

        for (; i < akibaArray.length; i++) {
            if (count >= strArray.length) {
                break;
            }
            if (strArray[count].equals(akibaArray[i])) {
                count++;
            } else if (!akibaArray[i].equals("A")) {
                flag = false;
                break;
            }

        }

        if (i <= akibaArray.length - 2 || count < strArray.length) {
            flag = false;
        }

        System.out.println(flag ? "YES" : "NO");
    }
}