import java.io.*;
import java.util.*;


public class Main {
	public static void main(String args[])throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(System.out);
		var st = new StringTokenizer(br.readLine()); 
		int n = Integer.parseInt(st.nextToken()); 
		var dist = Long.parseLong(st.nextToken());
		dist *= dist;
		int counter = 0;
		for(var i = 0; i < n; ++i) {
			st = new StringTokenizer(br.readLine());
			var x = Long.parseLong(st.nextToken());
			var y = Long.parseLong(st.nextToken());
			if((x*x + y*y) <= dist) counter++;
		}	
		pw.println(counter);
		pw.close();
	}

}
