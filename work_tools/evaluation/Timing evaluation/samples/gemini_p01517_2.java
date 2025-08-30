import java.util.*;

public class Main {
    static class Country {
        String name;
        int strength;
        List<String> neighbors;
        int id;

        public Country(String name, int strength, List<String> neighbors) {
            this.name = name;
            this.strength = strength;
            this.neighbors = neighbors;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int N = sc.nextInt();
            if (N == 0) {
                break;
            }

            Map<String, Country> countryMap = new HashMap<>();
            List<Country> countries = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                String name = sc.next();
                int strength = sc.nextInt();
                int C = sc.nextInt();
                List<String> neighbors = new ArrayList<>();
                for (int j = 0; j < C; j++) {
                    neighbors.add(sc.next());
                }
                Country country = new Country(name, strength, neighbors);
                country.id = i;
                countryMap.put(name, country);
                countries.add(country);
            }

            int startIndex = countryMap.get(countries.get(0).name).id;
            int maxStrength = 0;

            for (int i = 0; i < (1 << N); i++) {
                if (((i >> startIndex) & 1) == 0) { // Alliance must include the first country
                    continue;
                }

                boolean validAlliance = true;
                int currentStrength = 0;
                Set<Integer> allianceCountries = new HashSet<>();
                Set<Integer> forbiddenCountries = new HashSet<>();

                for (int j = 0; j < N; j++) {
                    if (((i >> j) & 1) == 1) { // If country j is in the alliance
                        allianceCountries.add(j);
                        currentStrength += countries.get(j).strength;
                        // Add neighbors of country j to forbidden list
                        for (String neighborName : countries.get(j).neighbors) {
                            forbiddenCountries.add(countryMap.get(neighborName).id);
                        }
                    }
                }

                for (int j = 0; j < N; j++) {
                    if (((i >> j) & 1) == 1) { // If country j is in the alliance
                        if (forbiddenCountries.contains(j)) { // Country j is a neighbor of another allied country
                            validAlliance = false;
                            break;
                        }
                        // Check if any neighbor of country j is also in the alliance
                        for (String neighborName : countries.get(j).neighbors) {
                            if (allianceCountries.contains(countryMap.get(neighborName).id)) {
                                validAlliance = false;
                                break;
                            }
                        }
                    }
                    if (!validAlliance) break;
                }

                if (validAlliance) {
                    maxStrength = Math.max(maxStrength, currentStrength);
                }
            }
            System.out.println(maxStrength);
        }
        sc.close();
    }
}