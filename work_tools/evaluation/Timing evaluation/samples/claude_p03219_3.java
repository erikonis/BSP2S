import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int X = sc.nextInt(); // Train fare A to B
        int Y = sc.nextInt(); // Bus fare B to C
        
        // Total cost = train fare + half bus fare
        int totalCost = X + Y / 2;
        
        System.out.println(totalCost);
        sc.close();
    }
}