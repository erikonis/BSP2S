import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;

import static java.lang.Math.*;
import static java.util.Arrays.*;

public class Main{

	Scanner sc=new Scanner(System.in);

	int INF=1<<28;
	double EPS=1e-9;

	int n;
	R[] rs;

	void run(){
		for(;;){
			n=sc.nextInt();
			if(n==0){
				break;
			}
			sc.nextLine();
			rs=new R[n];
			for(int j=0; j<n; j++){
				Scanner s=new Scanner(sc.nextLine());
				rs[j]=new R();
				rs[j].id=s.nextInt();
				for(int i=0; s.hasNext(); i++){
					rs[j].pins[i]=s.nextInt();
				}
			}
			solve();
		}
	}

	void solve(){
		for(int i=0; i<n; i++){
			rs[i].score=getScore(rs[i].pins);
		}
		Arrays.sort(rs);
		for(R r : rs){
			println(r.id+" "+r.score);
		}
	}

	int getScore(int[] pin){
		int score=0;
		for(int frame=0, i=0; frame<10; frame++){
			if(frame<9){
				if(pin[i]==10){
					score+=10+pin[i+1]+pin[i+2];
					i++;
				}else if(pin[i]+pin[i+1]==10){
					score+=10+pin[i+2];
					i+=2;
				}else{
					score+=pin[i]+pin[i+1];
					i+=2;
				}
			}else{
				score+=pin[i]+pin[i+1]+pin[i+2];
			}
		}
		return score;
	}

	class R implements Comparable<R>{
		int id, score;
		int[] pins=new int[22];

		@Override
		public int compareTo(R r){
			if(r.score!=score){
				return r.score-score;
			}else{
				return id-r.id;
			}
		}
	}

	void debug(Object... os){
		System.err.println(Arrays.deepToString(os));
	}

	void print(String s){
		System.out.print(s);
	}

	void println(String s){
		System.out.println(s);
	}

	public static void main(String[] args){
		// System.setOut(new PrintStream(new BufferedOutputStream(System.out)));
		new Main().run();
	}
}