import java.util.*;
import java.io.*;

class Main {

    public static void main(String[] args) {

	final Scanner stdin = new Scanner(System.in);

	while ( true ) {
	    final int n = stdin.nextInt();

	    if ( n == 0 ) {
		break;
	    }
	    
	    long[] a = new long[n];
	    for ( int i = 0; i < n; i++ ) {
		a[i] = stdin.nextLong();
	    }

	    long sum = 0;
	    Arrays.sort( a );
	    for ( int i = 0; i < n; i++ ) {
		sum +=  a[i] * ((long)(n - i - 1));
	    }
	    System.out.println( sum );
	}
	
    }
    
}