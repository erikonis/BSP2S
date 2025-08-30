import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int n = sc.nextInt();
            if (n == 0) {
                break;
            }

            int count = 0;
            long currentNum = n; 

            while (currentNum != 1) {
                if (currentNum % 2 == 0) {
                    currentNum /= 2;
                } else {
                    currentNum = currentNum * 3 + 1;
                }
                count++;
            }
            System.out.println(count);
        }
        sc.close();
    }
}