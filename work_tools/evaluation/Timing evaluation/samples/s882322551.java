import java.util.*;

public class Main {
    private class Data {
        int src, dst;

        public Data(int src, int dst) {
            this.src = src;
            this.dst = dst;
        }
    }

    public void main(Scanner sc) {
        int n = sc.nextInt();
        int m = sc.nextInt();
        Data data[] = new Data[m];
        for (int i = 0; i < m; i++) {
            data[i] = new Data(sc.nextInt(), sc.nextInt());
        }

        Arrays.sort(data, (o1, o2) -> o1.dst - o2.dst);

        int ans = 0;
        Data tmp = new Data(0, 0);
        for (int i = 0; i < m; i++) {
            if (!(data[i].src <= tmp.src && tmp.dst <= data[i].dst)) {
                ans++;
                tmp = new Data(data[i].dst - 1, data[i].dst);
            }
        }

        System.out.println(ans);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        new Main().main(sc);
        sc.close();
    }
}
