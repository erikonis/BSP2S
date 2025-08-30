import java.util.*;

public class Main{
  public Main(){}
  
  public static void main(String[] args){
    Scanner scanner = new Scanner(System.in);
    String line = scanner.nextLine();
    
    if(line.contains("a") || line.contains("e") || line.contains("i") || line.contains("o") || line.contains("u")){
 	  System.out.println("vowel");
    }
    else{
      System.out.println("consonant");
    }
  }
}