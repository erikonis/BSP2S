import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            int m = sc.nextInt();
            int n = sc.nextInt();
            
            if (m == 0 && n == 0) break;
            
            Set<Integer> players = new HashSet<>();
            for (int i = 1; i <= m; i++) {
                players.add(i);
            }
            
            int currentPlayer = 1;
            int currentNumber = 1;
            
            for (int i = 0; i < n; i++) {
                if (players.size() == 1) {
                    sc.nextLine();
                    continue;
                }
                
                // Find next active player
                while (!players.contains(currentPlayer)) {
                    currentPlayer = currentPlayer % m + 1;
                }
                
                String statement = sc.next();
                String expected = getExpectedStatement(currentNumber);
                
                if (!statement.equals(expected)) {
                    players.remove(currentPlayer);
                } else {
                    currentNumber++;
                }
                
                // Move to next player
                currentPlayer = currentPlayer % m + 1;
            }
            
            List<Integer> result = new ArrayList<>(players);
            Collections.sort(result);
            
            for (int i = 0; i < result.size(); i++) {
                if (i > 0) System.out.print(" ");
                System.out.print(result.get(i));
            }
            System.out.println();
        }
        
        sc.close();
    }
    
    private static String getExpectedStatement(int number) {
        if (number % 15 == 0) return "FizzBuzz";
        if (number % 3 == 0) return "Fizz";
        if (number % 5 == 0) return "Buzz";
        return String.valueOf(number);
    }
}