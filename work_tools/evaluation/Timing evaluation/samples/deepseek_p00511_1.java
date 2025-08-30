import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long result = Long.parseLong(scanner.nextLine());
        String op;
        
        while (!(op = scanner.nextLine()).equals("=")) {
            long num = Long.parseLong(scanner.nextLine());
            switch (op) {
                case "+":
                    result += num;
                    break;
                case "-":
                    result -= num;
                    break;
                case "*":
                    result *= num;
                    break;
                case "/":
                    result /= num;
                    break;
            }
        }
        System.out.println(result);
    }
}