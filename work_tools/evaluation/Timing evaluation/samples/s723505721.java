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

	void run(){
		for(;;){
			int n=sc.nextInt();
			if(n==0){
				break;
			}
			for(int i=0; i<n; i++){
				int pm=sc.nextInt();
				int pe=sc.nextInt();
				int pj=sc.nextInt();
				if((pm==100||pe==100||pj==100)||(pm+pe>=180)||(pm+pe+pj>=240)){
					println("A");
				}else if((pm+pe+pj>=210)||(pm+pe+pj>=150&&(pm>=80||pe>=80))){
					println("B");
				}else{
					println("C");
				}
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