import java.util.*;
import java.io.*;
import static java.lang.Math.*;

class Main {

    public static int[] primes( final int lim ) {

	boolean[] isPrime = new boolean[lim + 1];
	Arrays.fill( isPrime, true );
	isPrime[0] = isPrime[1] = false;

	final int indexLim = (int)sqrt( lim );
	int index = 2;
	while ( index <= lim ) {
	    for ( int i = index * 2; i <= lim; i += index ) {
		isPrime[i] = false;
	    }
	    index++;
	    while ( index <= lim && !isPrime[index] ){
		index++;
	    }		
	}
	final int count = count( isPrime, true );
	int[] answer = new int[count];
	index = 0;
	for ( int i = 0; i <= lim; i++ ) {
	    if ( isPrime[i] ) {
		answer[index] = i;
		index++;
	    }	    
	}
	return answer;
    }

    static int count( final boolean[] array, final boolean val ) {
	int count = 0;
	for ( boolean b : array ) {
	    if ( b == val ) {
		count++;
	    }
	}
	return count;
    }
    
    public static void main( final String[] args ) {

	final Scanner stdin = new Scanner( System.in );

	final int[] primes = primes( 1000000 );
	
	while ( true ) {

	    final int n = stdin.nextInt();
	    if ( n == 0 ){
		break;
	    }

	    int index = 0;
	    int count = 0;
	    while ( primes[index] <= n / 2 ) {
		if ( Arrays.binarySearch( primes, n - primes[index] ) > 0 ) {
		    count++;
		}
		index++;
	    }
	    System.out.println( count );
	}	
    }    
}