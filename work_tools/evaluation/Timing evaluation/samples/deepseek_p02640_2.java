import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int X = sc.nextInt();
        int Y = sc.nextInt();
        
        boolean found = false;
        for (int c = 0; c <= X; c++) {
            int t = X - c;
            if (2 * c + 4 * t == Y) {
                found = true;
                break;
            }
        }
        
        System.out.println(found ? "Yes" : "No");
    }
}