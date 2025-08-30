import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		Scanner stdIn = new Scanner(System.in);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        String upper_str = str.toUpperCase();
        System.out.println(upper_str);
	}
}