import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int n = sc.nextInt();
            if (n == 0) {
                break;
            }

            Map<String, Set<Integer>> characterAppearances = new HashMap<>();
            List<String> characterNames = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                String name = sc.next();
                characterNames.add(name);
                int m = sc.nextInt();
                Set<Integer> appearances = new HashSet<>();
                for (int j = 0; j < m; j++) {
                    appearances.add(sc.nextInt());
                }
                characterAppearances.put(name, appearances);
            }

            Map<String, Integer> characterScores = new HashMap<>();
            for (String name : characterNames) {
                characterScores.put(name, 0);
            }

            // Iterate through all possible time points (0 to 29, as d_i,j < 30)
            for (int time = 0; time < 30; time++) {
                List<String> appearingCharactersAtTime = new ArrayList<>();
                for (String name : characterNames) {
                    if (characterAppearances.get(name).contains(time)) {
                        appearingCharactersAtTime.add(name);
                    }
                }

                if (!appearingCharactersAtTime.isEmpty()) {
                    int numAppearing = appearingCharactersAtTime.size();
                    int pointsAwarded = n - (numAppearing - 1);
                    for (String charName : appearingCharactersAtTime) {
                        characterScores.put(charName, characterScores.get(charName) + pointsAwarded);
                    }
                }
            }

            int minScore = Integer.MAX_VALUE;
            String minCharName = "";

            // Find the character with the minimum score
            // If multiple characters have the same minimum score, choose the one with the lexicographically smallest name
            Collections.sort(characterNames); // Sort names to handle tie-breaking for min score

            for (String name : characterNames) {
                int score = characterScores.get(name);
                if (score < minScore) {
                    minScore = score;
                    minCharName = name;
                }
            }
            System.out.println(minScore + " " + minCharName);
        }
        sc.close();
    }
}