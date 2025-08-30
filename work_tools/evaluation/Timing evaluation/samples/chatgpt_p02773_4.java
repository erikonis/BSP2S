import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = Integer.parseInt(sc.nextLine());
        Map<String, Integer> countMap = new HashMap<>();
        for (int i = 0; i < N; i++) {
            String s = sc.nextLine();
            countMap.put(s, countMap.getOrDefault(s, 0) + 1);
        }
        int maxCount = 0;
        for (int v : countMap.values()) {
            if (v > maxCount) maxCount = v;
        }
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> e : countMap.entrySet()) {
            if (e.getValue() == maxCount) {
                result.add(e.getKey());
            }
        }
        Collections.sort(result);
        for (String s : result) {
            System.out.println(s);
        }
    }
}