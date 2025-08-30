import java.util.*;

public class Main{
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();
        String s1 = "";
        String s2 = "";
        s.nextLine();
        for(int i = 0; i < n; i++){
            s1 = s.nextLine();
            s2 = s.nextLine();
            System.out.println(findLongest(s1,s2));
        }
    }
    
    public static int findLongest(String x, String y){
    	//[row][col]
        int[][] arr = new int[x.length()+1][y.length()+1];
        //fill first row and first column with zeros
        for(int i = 0; i <= x.length(); i++) {
        	arr[i][0] = 0;
        }
        for(int i = 0; i <= y.length(); i++) {
        	arr[0][i] = 0;
        }
        //the number of rows is the length of y + 1
        int row = x.length()+1;
        int col = y.length()+1;
        //fill the rest of the array, from [1][1] to [x.length()][y.length()]
        for(int i = 1; i < row; i++) {
        	for(int j = 1; j < col; j++) {
        		if(x.charAt(i-1)==y.charAt(j-1)) {
        			arr[i][j] = 1 + arr[i-1][j-1];
        		}
        		else {
        			arr[i][j] = Math.max(arr[i-1][j],arr[i][j-1]);
        		}
        	}
        }
        return arr[x.length()][y.length()];
    } 
}
