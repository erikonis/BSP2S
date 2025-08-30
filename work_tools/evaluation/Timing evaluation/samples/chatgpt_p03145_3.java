import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int ab = sc.nextInt();
        int bc = sc.nextInt();
        int ca = sc.nextInt();

        // ∠ABC = 90°
        // Therefore, sides AB and BC are the two legs, CA is the hypotenuse.
        // Area = 1/2 * AB * BC

        int area = (ab * bc) / 2;
        System.out.println(area);
    }
}