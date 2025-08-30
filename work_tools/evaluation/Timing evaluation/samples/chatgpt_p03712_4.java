import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int H = sc.nextInt();
        int W = sc.nextInt();
        sc.nextLine(); // consume the rest of the line

        String[] image = new String[H];
        for (int i = 0; i < H; i++) {
            image[i] = sc.nextLine();
        }

        String border = "#".repeat(W + 2);

        System.out.println(border);
        for (int i = 0; i < H; i++) {
            System.out.println("#" + image[i] + "#");
        }
        System.out.println(border);
    }
}