import java.util.BitSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main{
	public static String r = "qwertasdfgzxcvb";
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		while(true){
			char[] s = in.next().toCharArray();
			if(s[0] == '#') break;
			boolean right = false;
			int count = 0;
			for(int i=0; i<s.length; i++){
				boolean hand = r.indexOf(s[i])!=-1;
				if(hand != right){
					right = hand;
					if(i>0) count++;
				}
			}
			System.out.println(count);
		}
	}
}