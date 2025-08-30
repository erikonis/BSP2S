// AOJ 2400 "審判は君だ！" (ICPC模擬国内予選 2012 Problem B)
import java.util.Scanner;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        final int PENALTY_TIME = 1200; // 1回の誤答で加算される時間
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            int t = sc.nextInt();
            int p = sc.nextInt();
            int r = sc.nextInt();
            if (t == 0) {
                break;
            }
            
            Team[] teamArray = new Team[t];
            for (int i = 0; i < teamArray.length; i++) {
                teamArray[i] = new Team(i + 1, p);
            }
            
            for (int i = 0; i < r; i++) {
                int tId = sc.nextInt();
                int pId = sc.nextInt();
                int time = sc.nextInt();
                String message = sc.next();
                
                if (message.equals("CORRECT")) {
                    teamArray[tId-1].ans++;
                    teamArray[tId-1].time += teamArray[tId-1].wrongAnswer[pId-1]
                                            * PENALTY_TIME + time;
                } else if (message.equals("WRONG")) {
                    teamArray[tId-1].wrongAnswer[pId-1]++;
                }
            }
            Arrays.sort(teamArray);
            for (int i = 0; i < teamArray.length; i++) {
                System.out.println(teamArray[i].id + " " + teamArray[i].ans + " " + teamArray[i].time);
            }
        }
    }
}

class Team implements Comparable<Team> {
    int id; // チーム番号
    int ans; // 正解数
    int time; // ペナルティ
    int[] wrongAnswer; // 各問題を間違えた回数
    
    Team(int id, int p) {
        this.id = id;
        this.ans = 0;
        this.time = 0;
        this.wrongAnswer = new int[p];
    }
    
    public int compareTo(Team o) {
        if (this.ans != o.ans) {
            return o.ans - this.ans;
        } else if (this.time != o.time) {
            return this.time - o.time;
        } else if (this.id != o.id) {
            return this.id - o.id;
        } else {
            // 問題の性質上、ここに来ることはないはず
            return 0;
        }
    }
}