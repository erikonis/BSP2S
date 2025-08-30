import java.util.*;

public class Main {
    static class Team {
        int id;
        int solved;
        int totalTime;
        
        Team(int id) {
            this.id = id;
            this.solved = 0;
            this.totalTime = 0;
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            int M = sc.nextInt();
            int T = sc.nextInt();
            int P = sc.nextInt();
            int R = sc.nextInt();
            
            if (M == 0 && T == 0 && P == 0 && R == 0) break;
            
            Team[] teams = new Team[T + 1];
            for (int i = 1; i <= T; i++) {
                teams[i] = new Team(i);
            }
            
            // Track submissions for each team-problem pair
            Map<String, List<Integer>> submissions = new HashMap<>();
            Map<String, Boolean> solved = new HashMap<>();
            
            for (int i = 0; i < R; i++) {
                int m = sc.nextInt();
                int t = sc.nextInt();
                int p = sc.nextInt();
                int j = sc.nextInt();
                
                String key = t + "-" + p;
                
                if (!solved.containsKey(key)) {
                    if (!submissions.containsKey(key)) {
                        submissions.put(key, new ArrayList<>());
                    }
                    submissions.get(key).add(m);
                    
                    if (j == 0) { // Correct submission
                        solved.put(key, true);
                        teams[t].solved++;
                        
                        // Calculate time: accepted time + 20 * wrong submissions
                        int wrongCount = submissions.get(key).size() - 1;
                        teams[t].totalTime += m + wrongCount * 20;
                    }
                }
            }
            
            // Sort teams
            List<Team> teamList = new ArrayList<>();
            for (int i = 1; i <= T; i++) {
                teamList.add(teams[i]);
            }
            
            teamList.sort((a, b) -> {
                if (a.solved != b.solved) {
                    return b.solved - a.solved; // More solved first
                }
                if (a.totalTime != b.totalTime) {
                    return a.totalTime - b.totalTime; // Less time first
                }
                return b.id - a.id; // Higher team number first for ties
            });
            
            // Group teams with same rank
            List<List<Integer>> groups = new ArrayList<>();
            List<Integer> currentGroup = new ArrayList<>();
            currentGroup.add(teamList.get(0).id);
            
            for (int i = 1; i < teamList.size(); i++) {
                Team prev = teamList.get(i - 1);
                Team curr = teamList.get(i);
                
                if (prev.solved == curr.solved && prev.totalTime == curr.totalTime) {
                    currentGroup.add(curr.id);
                } else {
                    groups.add(new ArrayList<>(currentGroup));
                    currentGroup.clear();
                    currentGroup.add(curr.id);
                }
            }
            groups.add(currentGroup);
            
            // Output
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < groups.size(); i++) {
                if (i > 0) result.append(",");
                
                List<Integer> group = groups.get(i);
                for (int j = 0; j < group.size(); j++) {
                    if (j > 0) result.append("=");
                    result.append(group.get(j));
                }
            }
            
            System.out.println(result.toString());
        }
    }
}