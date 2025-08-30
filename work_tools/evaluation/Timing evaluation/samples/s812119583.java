import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.next());
        String s = sc.next();
        int ans = 0;
        for (int a = 0; a <= 9; a++) {
            for (int b = 0; b <= 9; b++) {
                for (int c = 0; c <= 9; c++) {
                    boolean fa = false;
                    boolean fb = false;
                    boolean fc = false;

                    for (int i = 0; i < n; i++) {
                        if (!fa) {
                            if (s.substring(i, i + 1).equals(String.valueOf(a))) {
                                fa = true;
                            }
                        } else if (!fb) {
                            if (s.substring(i, i + 1).equals(String.valueOf(b))) {
                                fb = true;
                            }
                        } else if (!fc) {
                            if (s.substring(i, i + 1).equals(String.valueOf(c))) {
                                fc = true;
                            }
                        }
                        if (fa && fb && fc) {
                            ans++;
                            break;
                        }

                    }

                }
            }
        }

        System.out.println(ans);
        sc.close();
    }
}