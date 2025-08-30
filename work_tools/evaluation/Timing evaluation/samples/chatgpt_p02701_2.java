import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        Set<String> kinds = new HashSet<>();
        for (int i = 0; i < N; i++) {
            kinds.add(sc.next());
        }
        System.out.println(kinds.size());
    }
}