import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        
        Set<Integer> colors = new HashSet<>();
        colors.add(a);
        colors.add(b);
        colors.add(c);
        
        System.out.println(colors.size());
    }
}