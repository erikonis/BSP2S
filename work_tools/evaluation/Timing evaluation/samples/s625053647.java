import java.util.Scanner;

class Main{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		if( n<=52 ){
			int a[][] = new int[4][13];
			for( int i=0; i<4; i++ ){
				for( int j=0; j<13; j++ ){
					a[i][j] = 0;
				}
			}
			String pic;
			int rank;
			for( int i=0; i<n; i++ ){
				pic = sc.next();
				rank = sc.nextInt();
				if(pic.equals("S")){
					a[0][rank-1] = 1;
				} else if(pic.equals("H")){
					a[1][rank-1] = 1;
				} else if(pic.equals("C")){
					a[2][rank-1] = 1;
				} else if(pic.equals("D")){
					a[3][rank-1] = 1;
				} else{
				}
			}
			for( int i=0; i<4; i++ ){
				for( int j=0; j<13; j++ ){
					if( a[i][j] == 0 ){
						switch (i){
							case 0: System.out.print( "S " ); break;
							case 1: System.out.print( "H " ); break;
							case 2: System.out.print( "C " ); break;
							case 3: System.out.print( "D " ); break;
						}
						System.out.println( (j+1) );
					}
				}
			}
		}
	}
}