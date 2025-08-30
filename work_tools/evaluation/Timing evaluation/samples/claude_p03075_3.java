import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int a = sc.nextInt();
        int b = sc.nextInt();
        int c = sc.nextInt();
        int d = sc.nextInt();
        int e = sc.nextInt();
        int k = sc.nextInt();
        
        int[] antennas = {a, b, c, d, e};
        
        // Check all pairs of antennas
        for (int i = 0; i < 5; i++) {
            for (int j = i + 1; j < 5; j++) {
                int distance = antennas[j] - antennas[i];
                if (distance > k) {
                    System.out.println(":(");
                    return;
                }
            }
        }
        
        System.out.println("Yay!");
    }
}