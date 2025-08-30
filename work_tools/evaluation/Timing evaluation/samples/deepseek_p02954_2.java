import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String S = sc.next();
        int N = S.length();
        int[] res = new int[N];
        
        for (int i = 0; i < N; ) {
            if (S.charAt(i) == 'R') {
                int cnt = 0;
                while (i < N && S.charAt(i) == 'R') {
                    cnt++;
                    i++;
                }
                res[i - 1] += (cnt + 1) / 2;
                res[i] += cnt / 2;
            } else {
                int start = i;
                int cnt = 0;
                while (i < N && S.charAt(i) == 'L') {
                    cnt++;
                    i++;
                }
                res[start] += (cnt + 1) / 2;
                res[start - 1] += cnt / 2;
            }
        }
        
        for (int i = 0; i < N; i++) {
            System.out.print(res[i] + " ");
        }
    }
}