import java.util.*;

public class Main{
    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	int H = sc.nextInt();
	int W = sc.nextInt();
	for ( int i = 0; i < H; i++ ) {
	    for ( int j = 0; j < W; j++ ) {
		String S = sc.next();
		if ( S.equals("snuke") ) {
		    System.out.print((char)('A' + j));
		    System.out.println(i + 1);
		}
	    }	    
	}

    }
}
