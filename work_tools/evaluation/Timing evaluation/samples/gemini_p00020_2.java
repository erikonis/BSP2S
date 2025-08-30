import java.util.Scanner;

public class Capitalize {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text = sc.nextLine();
        System.out.println(text.toUpperCase());
        sc.close();
    }
}