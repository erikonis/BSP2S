
import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
	/**
	 * ??\?????????????????? / ????????????????????????
	 */
	public static void main(String[] args) throws Exception {

		// ??\???????????????
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int teamNum = Integer.parseInt(br.readLine());
		int gameNum = teamNum * (teamNum - 1) / 2;
		int[] scoreOfAll = new int[teamNum];
		
		for (int i = 0; i < gameNum; i++) {
			String[] readGame = br.readLine().split(" ");
			int nameOfTeamA = Integer.parseInt(readGame[0]) - 1;
			int nameOfTeamB = Integer.parseInt(readGame[1]) - 1;
			int scoreOfTeamA = Integer.parseInt(readGame[2]);
			int scoreOfTeamB = Integer.parseInt(readGame[3]);
			
			if (scoreOfTeamB < scoreOfTeamA) {
				scoreOfAll[nameOfTeamA] += 3;
			} else if (scoreOfTeamA < scoreOfTeamB) {
				scoreOfAll[nameOfTeamB] += 3;
			} else {
				scoreOfAll[nameOfTeamA] += 1;
				scoreOfAll[nameOfTeamB] += 1;
			}
		}
		
		 
		for (int scoreA : scoreOfAll) {
			int rank = 1;
			for (int scoreB : scoreOfAll) {
				if (scoreA < scoreB) {
					rank += 1;
				}
			}
			System.out.println(rank);
		}
		
	}

}