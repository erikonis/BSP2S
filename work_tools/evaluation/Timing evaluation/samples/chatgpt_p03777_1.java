import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String a = sc.next();
        String b = sc.next();

        // If AtCoDeer is honest, TopCoDeer is as AtCoDeer says.
        // If AtCoDeer is dishonest, TopCoDeer is the opposite of what AtCoDeer says.
        if (a.equals("H")) {
            System.out.println(b);
        } else {
            System.out.println(b.equals("H") ? "D" : "H");
        }
    }
}