import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {Main instance = new Main();sc = instance.new Scanner();instance.solve();}
    private class Scanner {String[] s = new String[0];String regex = " ";int i = 0;BufferedReader br = new BufferedReader(new InputStreamReader(System.in));public String next() {try {if (i < s.length) { return s[i++];}String st = br.readLine();while ("".equals(st)) { st = br.readLine(); }s = st.split(regex, 0);i = 0;return s[i++];} catch (IOException e) { return null; }}public int nextInt(){return Integer.parseInt(next());}}        
    private static Scanner sc;

    private void solve() {
        int H = sc.nextInt();
        int W = sc.nextInt();
        int D = sc.nextInt();
        int[] posx = new int[H*W];
        int[] posy = new int[H*W];
        int[] d = new int[H*W];
        
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                int A = sc.nextInt()-1;
                posx[A] = j;
                posy[A] = i;
            }
        }

        for (int i = D; i < H*W; i++) {
            int diffx = posx[i]-posx[i-D]; diffx = diffx < 0 ? -diffx : diffx;
            int diffy = posy[i]-posy[i-D]; diffy = diffy < 0 ? -diffy : diffy;
            d[i] += d[i-D] + diffx + diffy;
        }
        
        int Q = sc.nextInt();
        for (int i = 0; i < Q; i++) {
            System.out.println(-d[sc.nextInt()-1]+d[sc.nextInt()-1]);
        }
    }
}