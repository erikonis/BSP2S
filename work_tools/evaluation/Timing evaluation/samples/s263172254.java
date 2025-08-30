
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;
import java.util.Iterator;

class Main {

    static final int MOD = 1000000007;
    static int H;
    static int W;
    static int K;
    static char[][] base;
    static int[][] straw;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        H = sc.nextInt();
        W = sc.nextInt();
        K = sc.nextInt();
        base = new char[H][W];
        straw = new int[H][W];
        for (int y = 0; y < H; y++) {
            char[] str = sc.next().toCharArray();
            base[y] = str;
            for (int x = 0; x < W; x++) {
                straw[y][x] = str[x] == '#' ? -1 : 0;
            }
        }

        int iniY = 0;
        int iniX = 0;
        for (int block = 1; block <= K; block++) {

            init: for (int y = iniY; y < H; y++) {
                for (int x = 0; x < W; x++) {
                    if (straw[y][x] == -1) {
                        iniY = y;
                        iniX = x;
                        straw[iniY][iniX] = 0;
                        break init;
                    }
                }
            }
            while (iniX > 0 && straw[iniY][iniX - 1] == 0)
                iniX--;

            int ckx;
            for (ckx = iniX; ckx < W; ckx++) {
                if (straw[iniY][ckx] != 0) {
                    break;
                }
                //straw[iniY][ckx] = block;
            }            
            Arrays.fill(straw[iniY], iniX, ckx, block);
            expY: for (int y = iniY + 1; y < H; y++) {
                for (int x = iniX; x < ckx; x++) {
                    if (straw[y][x] != 0) {
                        break expY;
                    }
                }
                //System.out.println("expY"+ckx+" "+block);
                Arrays.fill(straw[y], iniX, ckx, block);
                // for (int x = iniX; x < ckx; x++) {
                // straw[y][x] = block;
                // }
            }
        }

        for (int y = H - 2; y >= 0; y--) {
            if (straw[y][0] == 0)
                straw[y] = straw[y + 1].clone();
        }

        for (int y = 0; y < H; y++) {
            String line = "";
            for (int x = 0; x < W; x++) {
                // System.out.print(String.format("%4d%c ",straw[y][x],base[y][x]));
                // System.out.print(straw[y][x] + " ");
                line += straw[y][x] + " ";
            }
            System.out.println(line);
        }
    }
}