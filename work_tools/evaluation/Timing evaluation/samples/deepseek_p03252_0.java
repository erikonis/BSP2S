import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String S = sc.next();
        String T = sc.next();
        
        HashMap<Character, Character> mapS = new HashMap<>();
        HashMap<Character, Character> mapT = new HashMap<>();
        boolean possible = true;
        
        for (int i = 0; i < S.length(); i++) {
            char sChar = S.charAt(i);
            char tChar = T.charAt(i);
            
            if (mapS.containsKey(sChar)) {
                if (mapS.get(sChar) != tChar) {
                    possible = false;
                    break;
                }
            } else {
                mapS.put(sChar, tChar);
            }
            
            if (mapT.containsKey(tChar)) {
                if (mapT.get(tChar) != sChar) {
                    possible = false;
                    break;
                }
            } else {
                mapT.put(tChar, sChar);
            }
        }
        
        System.out.println(possible ? "Yes" : "No");
    }
}