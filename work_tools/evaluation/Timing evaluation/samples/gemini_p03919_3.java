import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int H = sc.nextInt();
        int W = sc.nextInt();

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                String s = sc.next();
                if (s.equals("snuke")) {
                    // Convert column index to character (A=0, B=1, ...)
                    char colChar = (char) ('A' + j);
                    // Row index is 1-based
                    int rowNum = i + 1;
                    System.out.println(colChar + "" + rowNum);
                    sc.close();
                    return; // Found "snuke", so exit
                }
            }
        }
        sc.close();
    }
}