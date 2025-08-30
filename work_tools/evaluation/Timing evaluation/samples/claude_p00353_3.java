import java.util.Scanner;

public class Shopping {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt(); // my money
        int f = sc.nextInt(); // Alice's money
        int b = sc.nextInt(); // book price
        
        // If I already have enough money
        if (m >= b) {
            System.out.println(0);
        }
        // If even with all of Alice's money we can't afford it
        else if (m + f < b) {
            System.out.println("NA");
        }
        // Need to borrow some money from Alice
        else {
            System.out.println(b - m);
        }
        
        sc.close();
    }
}