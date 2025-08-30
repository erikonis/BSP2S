import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] asis = reader.readLine().split("");
        String[] tobe = reader.readLine().split("");
        int i = 0;
        int count =0;
        for (String s : asis) {
            if (!s.equals(tobe[i])) {
                count++;
            }
            i++;
        }
        System.out.println(count);

    }
}