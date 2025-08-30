import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
 
public class Main {

  public static void main(String[] args) throws IOException {
    try ( BufferedReader br = new BufferedReader(new InputStreamReader(System.in)) ) {

      String[] lines = br.readLine().split(" ");
      int a = Integer.parseInt(lines[0]);
      int b = Integer.parseInt(lines[1]);

      System.out.println((a+b) / 2);
    }
  }
}
