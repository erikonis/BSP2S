import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] parts = input.split(" ");
        String s = parts[1];
        char x = s.charAt(0);
        System.out.println("A" + x + "C");
    }
}