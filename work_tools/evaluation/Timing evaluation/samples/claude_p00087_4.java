import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) break;
            
            String[] tokens = line.split("\\s+");
            Stack<Double> stack = new Stack<>();
            
            for (String token : tokens) {
                if (token.equals("+")) {
                    double b = stack.pop();
                    double a = stack.pop();
                    stack.push(a + b);
                } else if (token.equals("-")) {
                    double b = stack.pop();
                    double a = stack.pop();
                    stack.push(a - b);
                } else if (token.equals("*")) {
                    double b = stack.pop();
                    double a = stack.pop();
                    stack.push(a * b);
                } else if (token.equals("/")) {
                    double b = stack.pop();
                    double a = stack.pop();
                    stack.push(a / b);
                } else {
                    // It's a number
                    stack.push(Double.parseDouble(token));
                }
            }
            
            System.out.printf("%.6f%n", stack.pop());
        }
        
        scanner.close();
    }
}