import java.util.*;

public class Main {
    static class Tile {
        int[] colors = new int[4];
        Tile(int a, int b, int c, int d) {
            colors[0] = a;
            colors[1] = b;
            colors[2] = c;
            colors[3] = d;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        Tile[] tiles = new Tile[N];
        for (int i = 0; i < N; i++) {
            tiles[i] = new Tile(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
        }

        long res = 0;
        for (int a = 0; a < N; a++) {
            for (int rotA = 0; rotA < 4; rotA++) {
                int[] topA = getRotated(tiles[a], rotA);
                for (int b = 0; b < N; b++) {
                    if (b == a) continue;
                    for (int rotB = 0; rotB < 4; rotB++) {
                        int[] topB = getRotated(tiles[b], rotB);
                        if (topA[1] != topB[0]) continue;
                        for (int c = 0; c < N; c++) {
                            if (c == a || c == b) continue;
                            for (int rotC = 0; rotC < 4; rotC++) {
                                int[] topC = getRotated(tiles[c], rotC);
                                if (topA[2] != topC[0] || topB[3] != topC[1]) continue;
                                for (int d = 0; d < N; d++) {
                                    if (d == a || d == b || d == c) continue;
                                    for (int rotD = 0; rotD < 4; rotD++) {
                                        int[] bottomD = getRotated(tiles[d], rotD);
                                        if (topA[3] != bottomD[0] || topB[2] != bottomD[1] || topC[3] != bottomD[2]) continue;
                                        for (int e = 0; e < N; e++) {
                                            if (e == a || e == b || e == c || e == d) continue;
                                            for (int rotE = 0; rotE < 4; rotE++) {
                                                int[] frontE = getRotated(tiles[e], rotE);
                                                if (topA[0] != frontE[0] || topB[0] != frontE[1] || topC[0] != frontE[2] || bottomD[0] != frontE[3]) continue;
                                                for (int f = 0; f < N; f++) {
                                                    if (f == a || f == b || f == c || f == d || f == e) continue;
                                                    for (int rotF = 0; rotF < 4; rotF++) {
                                                        int[] backF = getRotated(tiles[f], rotF);
                                                        if (topA[1] != backF[0] || topB[1] != backF[1] || topC[1] != backF[2] || bottomD[1] != backF[3]) continue;
                                                        res++;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println(res / 24); // Divide by 24 to account for cube rotations
    }

    static int[] getRotated(Tile tile, int rot) {
        int[] res = new int[4];
        for (int i = 0; i < 4; i++) {
            res[i] = tile.colors[(i + rot) % 4];
        }
        return res;
    }
}