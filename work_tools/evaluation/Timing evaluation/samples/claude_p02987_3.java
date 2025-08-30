import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        
        Map<Character, Integer> count = new HashMap<>();
        for (char c : s.toCharArray()) {
            count.put(c, count.getOrDefault(c, 0) + 1);
        }
        
        if (count.size() == 2) {
            boolean allTwo = true;
            for (int freq : count.values()) {
                if (freq != 2) {
                    allTwo = false;
                    break;
                }
            }
            System.out.println(allTwo ? "Yes" : "No");
        } else {
            System.out.println("No");
        }
    }
}