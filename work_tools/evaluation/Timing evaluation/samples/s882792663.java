import java.util.*;
import java.lang.*;
import java.io.*;

class Main {
	public static void main (String[] args) throws java.lang.Exception {
		// your code goes here
		BufferedReader stdReader = new BufferedReader(new InputStreamReader(System.in));
		String line = stdReader.readLine();
		int n = Integer.valueOf(line);
		LinkedList<Integer> li = new LinkedList<Integer>();
		for(int i = 0; i < n; i++) {
			line = stdReader.readLine();
			String[] sss = line.split(" ");
			if(sss[0].equals("insert")) {
				Integer o = Integer.valueOf(sss[1]);
				li.add(0, o);
			}else if(sss[0].equals("delete")){
				Integer o = Integer.valueOf(sss[1]);
				li.remove((Object) o);
			}else if(sss[0].equals("deleteFirst")) {
				li.remove(0);
			}else if(sss[0].equals("deleteLast")) {
				li.remove(li.size()-1);
			}
		}
		result(li);
	}
	public static void print(String s) {
		System.out.println(s);
	}
	public static void result(LinkedList<Integer> li) {
		StringBuilder bu = new StringBuilder();
		for(Integer i: li) {
			if(bu.length() > 0) {
				bu.append(" ");
			}
			bu.append(i);
		}
		print(bu.toString());
	}
}
