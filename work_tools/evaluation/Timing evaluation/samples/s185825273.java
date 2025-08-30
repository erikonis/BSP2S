import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(sc.nextLong());
        }
        System.out.println(list.stream().sorted(Comparator.reverseOrder()).skip(k).collect(Collectors.summingLong(Long::longValue)));
    }
}