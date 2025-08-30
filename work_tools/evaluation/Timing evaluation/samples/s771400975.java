import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * Membership Management
 */
public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		String[] words;

		while ((line = br.readLine()) != null && !line.isEmpty()) {

			int n = parseInt(line);
			if (n == 0) break;

			Map<String, Set<String>> groups = new HashMap<>();
			String first = "";

			for (int i = 0; i < n; i++) {
				words = br.readLine().replaceAll("\\.", "").split("[:,]");
				groups.put(words[0], new HashSet<>());
				for (int j = 1; j < words.length; j++) {
					groups.get(words[0]).add(words[j]);
				}
				if (i == 0) first = words[0];
			}

			//solve
			boolean changed;
			do {
				changed = false;
				for (String group : groups.keySet()) {
					if (!group.equals(first) && groups.get(first).contains(group)) {
						groups.get(first).remove(group);
						groups.get(first).addAll(groups.get(group));
						changed = true;
					}
				}
			} while (changed);

			System.out.println(groups.get(first).size());
		}
	}
}