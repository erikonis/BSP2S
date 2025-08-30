import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        Set<String> dictionary = new HashSet<>();
        
        for (int i = 0; i < n; i++) {
            String[] parts = br.readLine().split(" ");
            String command = parts[0];
            String str = parts[1];
            
            if (command.equals("insert")) {
                dictionary.add(str);
            } else if (command.equals("find")) {
                if (dictionary.contains(str)) {
                    System.out.println("yes");
                } else {
                    System.out.println("no");
                }
            }
        }
    }
}