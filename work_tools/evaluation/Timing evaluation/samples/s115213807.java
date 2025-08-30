import java.util.*;
import java.io.*;

public class Main{
  public static void main(String[] args)throws Exception{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try{
      String[] Sarr = br.readLine().split(" ");
      int N = Integer.parseInt(Sarr[0]);
      int K = Integer.parseInt(Sarr[1]);
      int H = Integer.parseInt(Sarr[2]);
      String S = br.readLine();
      
      int[] arr1 = new int[K];
      int index = 0;
      for(int i = 0; i < N; i++){
        char c = S.charAt(i);
        if(c == 'o'){
          arr1[index] = i+1;
          i += H;
          index++;
        }
      }
      
      int[] arr2 = new int[K];
      index = K-1;
      for(int i = S.length()-1; i >= 0; i--){
        char c = S.charAt(i);
        if(c == 'o'){
          arr2[index] = i+1;
          i -= H;
          index--;
        }
      }
      for(int i = 0; i < K; i++){
        if(arr1[i] == arr2[i]){
          System.out.println(arr1[i]);
        }
      }
    }catch(Exception e){
    }
  }
}
