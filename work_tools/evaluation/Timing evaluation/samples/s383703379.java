import java.io.*;
import java.util.*;

public class Main{
	public static void main(String args[]){
		try{
			new Main();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public Main() throws IOException{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String line;
		
		while((line = in.readLine()) != null){
			int size = Integer.parseInt(line);
			if(size==0) break;
			Bowl[] game = new Bowl[size];
			for(int i=0; i<size; i++){
				line = in.readLine();
				String[] tmp = line.split(" ");
				int id = Integer.parseInt(tmp[0]);
				int n = tmp.length-1;
				game[i] = new Bowl(id, n);
				for(int m=0; m<n; m++){
					game[i].pins[m] = Integer.parseInt(tmp[m+1]);
				}
				game[i].get_score();
			}
			
			Arrays.sort(game, new score_comp());
			
			for(int i=0; i<game.length; i++){
				System.out.printf("%d %d\n", game[i].id, game[i].score);
			}
		}
		
	}
}

class score_comp implements Comparator<Bowl>{
	public int compare(Bowl b1, Bowl b2){
		int d = 0;
		if((d = b1.score - b2.score) != 0)
			return -d;
		else
			return b1.id - b2.id;
	}
}

class Bowl{
	int[] pins;
	int n;
	int id;
	int score;
	
	public Bowl(int id, int n){
		this.id = id;
		this.n = n;
		this.score = 0;
		pins = new int[n];
	}
	
	public void get_score(){
		int m = 0;
		this.score = 0;
		for(int i=1; i<=10; i++){
			if(i==10){
				for(int k=m; k<n; k++){
					score += pins[k];
				}
			}
			else if(pins[m] == 10){
				score += pins[m] + pins[m+1] + pins[m+2];
				m++;
			}
			else{
				if(pins[m]+pins[m+1] == 10){
					score += pins[m] + pins[m+1] + pins[m+2];
				}
				else{
					score += pins[m] + pins[m+1];
				}
				m+=2;
			}
		}
	}
}