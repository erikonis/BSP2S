import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine(); // Consume the newline

        for (int i = 0; i < n; i++) {
            String s = sc.nextLine();
            System.out.println(reconstructTrain(s));
        }
    }

    private static String reconstructTrain(String record) {
        Map<Character, Integer> charToIndex = new HashMap<>(); // Maps char to its index in the current train
        List<Character> train = new ArrayList<>(); // Represents the current train formation

        String[] parts = record.split("((?<=[a-z])(->|<-))");

        char initialChar = parts[0].charAt(0);
        train.add(initialChar);
        charToIndex.put(initialChar, 0);
        int currentPos = 0; // Index of the current car in the 'train' list

        for (int j = 0; j < parts.length - 1; j++) {
            char currentChar = parts[j].charAt(0);
            char nextChar = parts[j + 1].charAt(0);
            String arrow = parts[j].substring(1);

            int nextPos;

            if (!charToIndex.containsKey(nextChar)) {
                // New character, add it to the train
                if (arrow.equals("->")) {
                    // Moving forward, add to the right
                    nextPos = currentPos + 1;
                    if (nextPos == train.size()) {
                        train.add(nextChar);
                    } else {
                        train.add(nextPos, nextChar);
                    }
                } else { // arrow.equals("<-")
                    // Moving backward, add to the left
                    nextPos = currentPos - 1;
                    if (nextPos < 0) {
                        train.add(0, nextChar);
                        nextPos = 0; // After adding at 0, its new index is 0
                    } else {
                        train.add(nextPos, nextChar);
                    }
                }
                // Update all indices after insertion
                charToIndex.clear();
                for (int k = 0; k < train.size(); k++) {
                    charToIndex.put(train.get(k), k);
                }
            }
            currentPos = charToIndex.get(nextChar);
        }

        StringBuilder sb = new StringBuilder();
        for (char c : train) {
            sb.append(c);
        }
        return sb.toString();
    }
}