import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String[] parts = input.split(" ");
        int A = Integer.parseInt(parts[0]);
        String op = parts[1];
        int B = Integer.parseInt(parts[2]);
        
        int result = op.equals("+") ? A + B : A - B;
        System.out.println(result);
    }
}