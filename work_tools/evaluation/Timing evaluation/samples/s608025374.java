import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = Integer.parseInt(sc.next());
        char[] S = sc.next().toCharArray();
        char preS = '.';
        int r = 0;
        for (char s:
             S) {
            if (preS!=s) r++;
            preS = s;
        }
        System.out.println(r);
    }
}