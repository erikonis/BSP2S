import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int X = sc.nextInt();
        
        int kyu = 9 - (X / 200);
        
        System.out.println(kyu);
    }
}