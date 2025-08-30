import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] days = { "mon", "tue", "wed", "thu", "fri", "sat", "sun" };

        int x = Integer.parseInt(br.readLine());
        System.out.println(days[(x+10)%7]); // x+14(2weeks)-9(base day)+5(sat)
    }
}
