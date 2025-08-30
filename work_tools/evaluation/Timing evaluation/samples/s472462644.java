import java.util.ArrayList;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.next());
        ArrayList<Integer> a = new ArrayList<>();
        int count = 0;

        for (int i = 0; i < n; i++) {
            int nextInt = sc.nextInt();
            a.add(nextInt);
            if (i > nextInt - 1 && a.get(nextInt-1) == i + 1) {
                ++count;
            }
        }

        System.out.println(count);
        

    }
}