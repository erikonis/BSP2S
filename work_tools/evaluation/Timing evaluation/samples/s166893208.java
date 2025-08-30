
import java.util.*;

public class Main {

    static long s(long k) {
        char[] kk = String.valueOf(k).toCharArray();
        long ret = 0;
        for (int i = 0; i < kk.length; i++) {
            ret += kk[i] - '0';
        }
        return ret;
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt();
        ArrayList<Long> l = new ArrayList<>();
        for (int i = 1; i < 10; i++)
            l.add((long) i);
        long add = 1;
        int num = 0;
        while (true) {

            for (int i = 0; i < 1001; i++) {
                long ttk = add * i - 1;
                if (ttk < 1)
                    continue;
                l.add(ttk);
            }
            Collections.sort(l);
            for (int i = 0; i < l.size(); i++) {
                for (int j = i + 1; j < l.size(); j++) {
                    if (l.get(i).longValue() == l.get(j).longValue()) {
                        l.remove(j);
                        j--;
                        continue;
                    }
                }
            }

            IN: for (int i = 0; i < l.size(); i++) {
                double hoge = (double) l.get(i) / s(l.get(i));
                for (int j = i + 1; j < l.size(); j++) {
                    double kkk = (double) l.get(j) / s(l.get(j));
                    if (kkk < hoge) {
                        l.remove(i);
                        i--;
                        continue IN;
                    }
                }
            }

            if (num == 2)
                break;
            if (l.size() >= k) {
                num++;
            }

            add *= 10L;

        }

        for (int i = 0; i < k; i++) {
            System.out.println(l.get(i));
        }

    }

}
