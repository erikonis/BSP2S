import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        Map<Integer, Integer> teamScores = new HashMap<>();
        
        // Read team data
        while (true) {
            String line = sc.nextLine();
            String[] parts = line.split(",");
            int teamId = Integer.parseInt(parts[0]);
            int score = Integer.parseInt(parts[1]);
            
            if (teamId == 0 && score == 0) break;
            
            teamScores.put(teamId, score);
        }
        
        // Get unique scores and sort them in descending order
        Set<Integer> uniqueScores = new TreeSet<>(Collections.reverseOrder());
        uniqueScores.addAll(teamScores.values());
        
        // Create rank mapping
        Map<Integer, Integer> scoreToRank = new HashMap<>();
        int rank = 1;
        for (int score : uniqueScores) {
            scoreToRank.put(score, rank++);
        }
        
        // Process queries
        while (sc.hasNextLine()) {
            int queryTeam = Integer.parseInt(sc.nextLine());
            int teamScore = teamScores.get(queryTeam);
            System.out.println(scoreToRank.get(teamScore));
        }
        
        sc.close();
    }
}