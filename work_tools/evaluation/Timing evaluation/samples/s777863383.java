import java.util.*;
import java.io.*;
public class Main {
        public static void main(String[] args){
                Scanner sc = new Scanner(System.in);
                Map hs = new HashMap();
                PrintWriter ou = new PrintWriter(System.out);
                String s = sc.next();
                sc.close();
                char[] c = new char[s.length()];
                for(int i = 0 ; i < s.length() ; i++){
                        c[i] = s.charAt(i);
                }
                ou.print("2018/01/" + c[8] + c[9] + "\n");
                ou.flush();
        }
}