import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();
        sc.close();

        StringBuilder sbA = new StringBuilder();
        for (int i = 0; i < b; i++) {
            sbA.append(a);
        }
        String s1 = sbA.toString();

        StringBuilder sbB = new StringBuilder();
        for (int i = 0; i < a; i++) {
            sbB.append(b);
        }
        String s2 = sbB.toString();

        if (s1.compareTo(s2) <= 0) {
            System.out.println(s1);
        } else {
            System.out.println(s2);
        }
    }
}