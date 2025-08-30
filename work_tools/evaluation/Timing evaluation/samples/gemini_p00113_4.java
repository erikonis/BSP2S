import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            int p = sc.nextInt();
            int q = sc.nextInt();

            StringBuilder result = new StringBuilder();
            StringBuilder caretLine = new StringBuilder();
            HashMap<Integer, Integer> remainders = new HashMap<>();
            ArrayList<Integer> digits = new ArrayList<>();

            int position = 0;
            p *= 10; // Start with the first decimal digit

            while (p != 0 && !remainders.containsKey(p)) {
                remainders.put(p, position);
                int digit = p / q;
                digits.add(digit);
                p = (p % q) * 10;
                position++;
            }

            if (p == 0) { // Terminating decimal
                for (int digit : digits) {
                    result.append(digit);
                }
                System.out.println(result.toString());
            } else { // Repeating decimal
                int repeatingStart = remainders.get(p);
                for (int i = 0; i < digits.size(); i++) {
                    result.append(digits.get(i));
                    if (i >= repeatingStart) {
                        caretLine.append("^");
                    } else {
                        caretLine.append(" ");
                    }
                }
                System.out.println(result.toString());
                System.out.println(caretLine.toString());
            }
        }
        sc.close();
    }
}