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
			int n=5;
			int[] a=new int[n];
			boolean[] exist=new boolean[3];
			for(int i=0; i<n; i++){
				if((a[i]=sc.nextInt()-1)==-1){
					return;
				}
				exist[a[i]]=true;
			}
			int s=0;
			for(int i=0; i<3; i++){
				if(exist[i]){
					s++;
				}
			}
			if(s==2){
				for(int i=0; i<n; i++){
					if(exist[(a[i]+1)%3]){
						println("1");
					}else{
						println("2");
					}
				}
			}else{
				println("3\n3\n3\n3\n3");
				continue;
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