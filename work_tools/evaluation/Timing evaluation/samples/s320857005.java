import java.util.*;

public class Main {

    public static void main(String args[]) {

        // 入力
        Scanner sc = new Scanner(System.in);
        int a = Integer.parseInt(sc.next());
        int b = Integer.parseInt(sc.next());
        int c = Integer.parseInt(sc.next());
        sc.close();

        // 主処理
        int max = Math.max(Math.max(a, b), c);
        int[] diff = new int[] { max - a, max - b, max - c };
        int count = 0;
        for (int i = 0; i < 3; i++) {
            count += diff[i] / 2;
            diff[i] %= 2;
        }
        int sum = Arrays.stream(diff).sum();
        count += sum % 2 == 0 ? sum / 2 : sum / 2 + 2;

        int result = count;

        // 出力
        System.out.println(result);
    }
}
