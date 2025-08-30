import java.util.*;
 
public class Main{
  public static void main(String[]args){
    Scanner sc =  new Scanner(System.in);
    String S = sc.nextLine();
    
    switch(S){
      case "a":
      case "i":
      case "u":
      case "e":
      case "o":
        System.out.println("vowel");
        return;
    }
    
    System.out.println("consonant");
  }
}