import java.util.Scanner;

public class CandyDistribution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();
        int c = sc.nextInt();

        // Calculate the total number of candies
        int totalCandies = a + b + c;

        // If the total number of candies is odd, it's impossible to divide them equally.
        if (totalCandies % 2 != 0) {
            System.out.println("No");
            return;
        }

        // The target number of candies for each student
        int target = totalCandies / 2;

        // Check if any single pack equals the target
        // Or if the sum of any two packs equals the target
        if (a == target || b == target || c == target ||
            a + b == target || a + c == target || b + c == target) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }
}