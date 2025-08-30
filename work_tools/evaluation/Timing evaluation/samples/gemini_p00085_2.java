import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            if (n == 0 && m == 0) {
                break;
            }

            LinkedList<Integer> participants = new LinkedList<>();
            for (int i = 1; i <= n; i++) {
                participants.add(i);
            }

            int currentPotatoHolderIndex = n - 1; // Participant n (index n-1) starts with the potato

            while (participants.size() > 1) {
                // Pass the potato m-1 times to the right
                currentPotatoHolderIndex = (currentPotatoHolderIndex + (m - 1)) % participants.size();
                
                // The person who receives the potato on the m-th pass is removed
                participants.remove(currentPotatoHolderIndex);

                // If the removed person was the last in the list, the next person to receive the potato
                // is the first person (index 0) in the remaining list.
                // Otherwise, the index remains the same as the list shrinks.
                if (currentPotatoHolderIndex == participants.size()) {
                    currentPotatoHolderIndex = 0;
                }
            }
            System.out.println(participants.get(0));
        }
        sc.close();
    }
}