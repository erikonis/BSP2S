import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int[] arr = new int[4];
        for(int i=0; i<4; i++) arr[i] = sc.nextInt();

        int left = arr[0] + arr[1];
        int right = arr[2] + arr[3];

        System.out.println(left > right ? "Left" : left < right ? "Right" : "Balanced");
    }
}