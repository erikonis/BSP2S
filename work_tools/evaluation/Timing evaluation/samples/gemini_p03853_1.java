import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int H = sc.nextInt();
        int W = sc.nextInt();

        // Consume the rest of the line after reading W
        sc.nextLine(); 

        String[] image = new String[H];
        for (int i = 0; i < H; i++) {
            image[i] = sc.nextLine();
        }

        for (int i = 0; i < H; i++) {
            System.out.println(image[i]);
            System.out.println(image[i]);
        }

        sc.close();
    }
}