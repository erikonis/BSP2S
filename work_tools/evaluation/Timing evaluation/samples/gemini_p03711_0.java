import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt();
        int y = sc.nextInt();

        // Define the groups based on the image
        int[] group1 = {1, 3, 5, 7, 9, 11};
        int[] group2 = {2, 4, 6, 8, 10, 12};
        int[] group3 = {}; // This group is empty in this problem as 1-12 are all in group1 or group2 based on odd/even

        boolean xInGroup1 = false;
        boolean xInGroup2 = false;
        boolean yInGroup1 = false;
        boolean yInGroup2 = false;

        // Check if x is in group 1
        for (int num : group1) {
            if (x == num) {
                xInGroup1 = true;
                break;
            }
        }
        // Check if x is in group 2
        for (int num : group2) {
            if (x == num) {
                xInGroup2 = true;
                break;
            }
        }

        // Check if y is in group 1
        for (int num : group1) {
            if (y == num) {
                yInGroup1 = true;
                break;
            }
        }
        // Check if y is in group 2
        for (int num : group2) {
            if (y == num) {
                yInGroup2 = true;
                break;
            }
        }

        // Determine if they belong to the same group
        if ((xInGroup1 && yInGroup1) || (xInGroup2 && yInGroup2)) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }

        sc.close();
    }
}