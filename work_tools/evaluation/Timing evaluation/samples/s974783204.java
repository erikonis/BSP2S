import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Problem P04: CamelCase
 * Java 7 later.
 */
public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		String[] words;

		while ((line = br.readLine()) != null && !line.isEmpty()) {

			String name = line.substring(0, line.indexOf(' '));
			String type = line.substring(line.indexOf(' ') + 1);
			if (type.equals("X")) break;

			ArrayList<StringBuilder> tokens = new ArrayList<>();
			tokens.add(new StringBuilder());

			for (char c : name.toCharArray()) {
				if (c == '_') {
					tokens.add(new StringBuilder());
					continue;
				} else if ('A' <= c && c <= 'Z') {
					if (tokens.get(0).length() != 0) {
						tokens.add(new StringBuilder());
					}
					c += 32;
				}
				tokens.get(tokens.size() - 1).append(c);
			}

			StringBuilder out = new StringBuilder();
			switch (type) {
				case "U":
					for (int i = 0; i < tokens.size(); i++) {
						String token = tokens.get(i).toString();
						token = (char) (token.charAt(0) - 32) + token.substring(1);
						out.append(token);
					}
					break;
				case "L":
					for (int i = 0; i < tokens.size(); i++) {
						String token = tokens.get(i).toString();
						if (i > 0) {
							token = (char) (token.charAt(0) - 32) + token.substring(1);
						}
						out.append(token);
					}
					break;
				case "D":
					for (int i = 0; i < tokens.size(); i++) {
						if (i > 0) out.append("_");
						out.append(tokens.get(i).toString());
					}
					break;
			}
			System.out.println(out.toString());
		} //end while
	} //end main
}