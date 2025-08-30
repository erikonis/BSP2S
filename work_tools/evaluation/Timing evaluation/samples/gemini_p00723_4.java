import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(countDistinctConfigurations(line));
        }
    }

    private static int countDistinctConfigurations(String s) {
        Set<String> distinctConfigs = new HashSet<>();
        int n = s.length();

        for (int i = 1; i < n; i++) { // Split point
            String s1 = s.substring(0, i);
            String s2 = s.substring(i);

            Set<String> s1Orientations = getOrientations(s1);
            Set<String> s2Orientations = getOrientations(s2);

            for (String o1 : s1Orientations) {
                for (String o2 : s2Orientations) {
                    distinctConfigs.add(o1 + o2);
                    distinctConfigs.add(o2 + o1);
                }
            }
        }
        return distinctConfigs.size();
    }

    private static Set<String> getOrientations(String s) {
        Set<String> orientations = new HashSet<>();
        orientations.add(s);
        orientations.add(new StringBuilder(s).reverse().toString());
        return orientations;
    }
}