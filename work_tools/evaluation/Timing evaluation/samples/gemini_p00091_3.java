import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static int N;
    static int[][] initialGrid;
    static int[][] currentGrid;
    static List<Drop> drops;

    static final int[][] SMALL_BLUR = {
            {0, 0, 0},
            {0, 1, 0},
            {0, 0, 0}
    };

    static final int[][] MEDIUM_BLUR = {
            {0, 1, 0},
            {1, 1, 1},
            {0, 1, 0}
    };

    static final int[][] LARGE_BLUR = {
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}
    };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        initialGrid = new int[10][10];
        currentGrid = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                initialGrid[i][j] = sc.nextInt();
                currentGrid[i][j] = initialGrid[i][j];
            }
        }
        sc.close();

        drops = new ArrayList<>();
        solve();

        for (Drop drop : drops) {
            System.out.println(drop.x + " " + drop.y + " " + drop.type);
        }
    }

    static boolean solve() {
        if (drops.size() == N) {
            return checkGrid();
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (currentGrid[i][j] > 0) {
                    // Try large drop (type 3)
                    if (canApply(i, j, LARGE_BLUR)) {
                        applyDrop(i, j, LARGE_BLUR, 3);
                        if (solve()) return true;
                        unapplyDrop(i, j, LARGE_BLUR, 3);
                    }

                    // Try medium drop (type 2)
                    if (canApply(i, j, MEDIUM_BLUR)) {
                        applyDrop(i, j, MEDIUM_BLUR, 2);
                        if (solve()) return true;
                        unapplyDrop(i, j, MEDIUM_BLUR, 2);
                    }

                    // Try small drop (type 1)
                    if (canApply(i, j, SMALL_BLUR)) {
                        applyDrop(i, j, SMALL_BLUR, 1);
                        if (solve()) return true;
                        unapplyDrop(i, j, SMALL_BLUR, 1);
                    }
                    return false; // If a cell > 0 is found, but no drop can be applied, this path is invalid
                }
            }
        }
        // If all cells are 0 and N drops haven't been applied yet, it means there are redundant drops in the solution.
        // This should only be reached if the grid is all zeros and drops.size() < N.
        // However, the problem guarantees a solution with exactly N drops that results in the given grid.
        // So, if we reach here and the grid is all 0s, it means we have successfully accounted for all drops.
        return checkGrid();
    }


    static boolean checkGrid() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (currentGrid[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    static boolean canApply(int x, int y, int[][] blur) {
        int blurSize = blur.length;
        int halfSize = blurSize / 2;

        for (int i = 0; i < blurSize; i++) {
            for (int j = 0; j < blurSize; j++) {
                if (blur[i][j] == 1) {
                    int nx = x + (i - halfSize);
                    int ny = y + (j - halfSize);
                    if (nx < 0 || nx >= 10 || ny < 0 || ny >= 10 || currentGrid[nx][ny] == 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    static void applyDrop(int x, int y, int[][] blur, int type) {
        int blurSize = blur.length;
        int halfSize = blurSize / 2;

        for (int i = 0; i < blurSize; i++) {
            for (int j = 0; j < blurSize; j++) {
                if (blur[i][j] == 1) {
                    int nx = x + (i - halfSize);
                    int ny = y + (j - halfSize);
                    currentGrid[nx][ny]--;
                }
            }
        }
        drops.add(new Drop(x, y, type));
    }

    static void unapplyDrop(int x, int y, int[][] blur, int type) {
        int blurSize = blur.length;
        int halfSize = blurSize / 2;

        for (int i = 0; i < blurSize; i++) {
            for (int j = 0; j < blurSize; j++) {
                if (blur[i][j] == 1) {
                    int nx = x + (i - halfSize);
                    int ny = y + (j - halfSize);
                    currentGrid[nx][ny]++;
                }
            }
        }
        drops.remove(drops.size() - 1);
    }

    static class Drop {
        int x, y, type;

        public Drop(int x, int y, int type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }
    }
}